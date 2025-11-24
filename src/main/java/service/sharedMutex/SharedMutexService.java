package service.sharedMutex;

import model.NetworkAddress;
import model.OtherNode;
import service.NodeService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class SharedMutexService {
    private static final Logger logger = Logger.getLogger(SharedMutexService.class.getName());
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    /**
     * thread pool executor of receive tasks
     */
    private final ExecutorService messageHandlerPool = Executors.newSingleThreadExecutor(MessageHandlerThreadFactory.FACTORY);

    /**
     * State of the node
     */
    private final AtomicReference<NodeState> state = new AtomicReference<>(NodeState.IDLE);

    /**
     * Lamport clock
     */
    private final AtomicInteger lamportClock = new AtomicInteger(0);

    /**
     * Timestamp of our current REQUEST (meaningful only in REQUESTING state)
     */
    private final AtomicInteger requestTimestamp = new AtomicInteger(Integer.MAX_VALUE);

    /**
     * Map of nodeNetworkAddress -> node
     */
    private final ConcurrentHashMap<NetworkAddress, OtherNode> otherNodesMap;

    private final NetworkAddress nodeNetworkAddress;
    private final NodeService nodeService;

    public SharedMutexService(ConcurrentHashMap<NetworkAddress, OtherNode> otherNodesMap, NodeService nodeService) {
        this.otherNodesMap = otherNodesMap;
        this.nodeNetworkAddress = nodeService.getNodeAddress();
        this.nodeService = nodeService;
    }

    public void requestCriticalSection() {
        if (state.get() == NodeState.HOLDING) {
            logger.warning("Node is already in CRITICAL SECTION! You have to leave first to enter again!");
            return;
        }
        synchronizeWithPeersForCriticalSection();
        enterCriticalSection();
    }

    public void leaveCriticalSection() {
        logger.info("Node EXITING critical section%n");
        lock.lock();
        try {
            state.set(NodeState.IDLE);

            // Send REPLY to all deferred requests
            otherNodesMap.forEach(((networkAddress, otherNode) -> {
                if (Boolean.TRUE.equals(otherNode.getIsDeferred().get())) {
                    sendReplyTo(networkAddress);
                }
                otherNode.getIsDeferred().set(Boolean.FALSE);
            }));

        } finally {
            lock.unlock();
        }
    }

    private void sendRequestTo(NetworkAddress receiverNodeNetworkAddress) {
        OtherNode otherNode = otherNodesMap.get(receiverNodeNetworkAddress);
        otherNode.getGrpcClient().sendRequest(lamportClock.get());
    }

    private void sendReplyTo(NetworkAddress receiverNodeNetworkAddress) {
        OtherNode otherNode = otherNodesMap.get(receiverNodeNetworkAddress);
        otherNode.getHasGranted().set(Boolean.FALSE);
        otherNode.getGrpcClient().sendReply();
    }


    public void registerRequest(int senderTimestamp, NetworkAddress senderNodeNetworkAddress) {
        messageHandlerPool.submit(() -> receiveRequest(senderTimestamp, senderNodeNetworkAddress));
    }

    public void registerReply(NetworkAddress senderNodeNetworkAddress) {
        messageHandlerPool.submit(() -> receiveReply(senderNodeNetworkAddress));
    }

    private void synchronizeWithPeersForCriticalSection() {
        lock.lock();
        try {
            state.set(NodeState.REQUESTING);

            // Lamport timestamp for this request
            requestTimestamp.set(lamportClock.incrementAndGet());

            // Send REQUEST(ts, nodeNetworkAddress) to all others
            otherNodesMap.entrySet().stream()
                    .filter(entry -> !entry.getValue().getHasGranted().get())
                            .forEach(entry -> sendRequestTo(entry.getKey()));

            // Wait until we have REPLY from everyone
            waitForAllReplies();

            // We now have logical permission from all
            state.set(NodeState.HOLDING);
        } finally {
            lock.unlock();
        }
    }

    private void waitForAllReplies() {
        try {
            while (!haveAllReplied()) {
                condition.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private boolean haveAllReplied() {
        return otherNodesMap.values().stream()
                .map(OtherNode::getHasGranted)
                .allMatch(AtomicBoolean::get);
    }

    private void enterCriticalSection() {
        logger.info("Node ENTERING critical section%n");
        // critical section overwriting the shared variable
        int newSharedVariable = nodeService.getSharedVariable() + 1;
        nodeService.setSharedVariable(newSharedVariable);

        final int valueToSend = newSharedVariable;

        // send the shared variable update
        // todo think about sending the update in the leaveCriticalSection() - better see the algorithm working
        otherNodesMap.forEach((nodeNetworkAddress, otherNode) -> otherNode.getGrpcClient().updateSharedVariable(valueToSend));
    }

    // todo when the clocks match compare the network addresses
    private void receiveRequest(int otherTimestamp, NetworkAddress otherNodeNetworkAddress) {
        lock.lock();
        try {
            // Lamport clock update
            lamportClock.updateAndGet(c -> Math.max(c, otherTimestamp) + 1);

            boolean iHavePriority;

            NodeState s = state.get();

            if (s == NodeState.HOLDING) {
                // I'm in the Critical Section → always keep priority
                iHavePriority = true;
            } else if (s == NodeState.REQUESTING) {
                int myTs = requestTimestamp.get();
                if (myTs < otherTimestamp) {
                    iHavePriority = true;
                } else if (myTs > otherTimestamp) {
                    iHavePriority = false;
                } else {
                    // tie-breaker: lower node network address wins
                    iHavePriority = nodeNetworkAddress.compareTo(otherNodeNetworkAddress) < 0;
                }
            } else { // IDLE
                iHavePriority = false;
            }

            if (iHavePriority) {
                // Defer reply until we leave Critical Section
                Optional.ofNullable(otherNodesMap.get(otherNodeNetworkAddress))
                        .ifPresent(node -> node.getIsDeferred().set(Boolean.TRUE));

            } else {
                // Send REPLY now
                sendReplyTo(otherNodeNetworkAddress);
            }
        } finally {
            lock.unlock();
        }
    }

    private void receiveReply(NetworkAddress otherNodeNetworkAddress) {
        lock.lock();
        try {
            Optional.ofNullable(otherNodesMap.get(otherNodeNetworkAddress))
                    .filter(otherNode -> !otherNode.getHasGranted().get())
                    .ifPresent(otherNode -> {
                        otherNode.getHasGranted().set(Boolean.TRUE);
                        condition.signalAll();
                    });
        } finally {
            lock.unlock();
        }
    }
}
