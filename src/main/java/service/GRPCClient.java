package service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import model.NetworkAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proto.common.Address;
import proto.common.SharedVariableUpdate;
import proto.connection.AllNetworkNodes;
import proto.connection.Join;
import proto.connection.JoinNetworkGrpc;
import proto.connection.JoinResponse;
import proto.connection.NodeDied;
import proto.sharedMutex.Reply;
import proto.sharedMutex.Request;
import proto.sharedMutex.SharedMutexGrpc;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class.getName());
    private final ManagedChannel channel;
    private final JoinNetworkGrpc.JoinNetworkBlockingStub joinNetworkBlockingStub;
    private final SharedMutexGrpc.SharedMutexBlockingStub sharedMutexBlockingStub;
    private final NodeService nodeService;
    private final NetworkAddress serverNetworkAddress;
    private UUID instanceHoldingId = null;

    public GRPCClient(NetworkAddress serverNetworkAddress, NodeService nodeService, UUID instanceHoldingId) {
        this(serverNetworkAddress, nodeService);
        this.instanceHoldingId = instanceHoldingId;
    }

    public GRPCClient(NetworkAddress serverNetworkAddress, NodeService nodeService) {
        this(
                serverNetworkAddress,
                ManagedChannelBuilder.forAddress(serverNetworkAddress.ipAddress(), serverNetworkAddress.port())
                        .usePlaintext()
                        .build(),
                nodeService
        );
    }

    private GRPCClient(NetworkAddress serverNetworkAddress, ManagedChannel managedChannel, NodeService nodeService) {
        this.serverNetworkAddress = serverNetworkAddress;
        this.channel = managedChannel;
        this.nodeService = nodeService;
        joinNetworkBlockingStub = JoinNetworkGrpc.newBlockingStub(channel);
        sharedMutexBlockingStub = SharedMutexGrpc.newBlockingStub(channel);
    }

    public void joinWithNodesRequest() {
        joinWithNodesRequest(
                Join.newBuilder()
                        .setAddress(
                                Address.newBuilder().setIpAddress(nodeService.getNodeAddress().ipAddress())
                                        .setPort(nodeService.getNodeAddress().port())
                                        .build()
                        ).build());
    }

    public void joinToNetwork() {
        joinToNetwork(
                Join.newBuilder()
                        .setAddress(
                                Address.newBuilder().setIpAddress(nodeService.getNodeAddress().ipAddress())
                                        .setPort(nodeService.getNodeAddress().port())
                                        .build()
                        ).build());
    }

    public void ping() {
        Address address = GRPCServer.networkAddressToAddress(nodeService.getNodeAddress());
        try {
            joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .ping(address);
            logger.info("Successfully pinged node: {}", serverNetworkAddress);
        } catch (StatusRuntimeException e) {
            logger.warn("ping to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public void nodeDiedInfo(NetworkAddress nodeAddress, UUID diedInstanceUUID) {
        logger.info("Sending nodeDiedInfo to node: {}", serverNetworkAddress);
        NodeDied nodeDied = NodeDied.newBuilder()
                .setServerId(diedInstanceUUID.toString())
                .setAddress(
                        GRPCServer.networkAddressToAddress(nodeAddress)
                ).build();
        try {
            joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .nodeDiedInfo(nodeDied);
        } catch (StatusRuntimeException e) {
            logger.warn("NodeDiedInfo to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    private void joinWithNodesRequest(Join joinRequest) {
        logger.info("Sending joinWithNodesRequest to node: {}", serverNetworkAddress);
        try {
            AllNetworkNodes allNetworkNodes = joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .joinWithNodesRequest(joinRequest);
            instanceHoldingId = UUID.fromString(allNetworkNodes.getReceiverServerId());
            nodeService.setSharedVariable(allNetworkNodes.getSharedVariableUpdate().getNewValue());
            logger.info("Successfully joined to network: {}", allNetworkNodes);
            for (Join join : allNetworkNodes.getNodesAddressesList()) {
                Address address = join.getAddress();
                nodeService.connectToNode(new NetworkAddress(address.getIpAddress(), address.getPort()), UUID.fromString(join.getServerId()));
            }
        } catch (StatusRuntimeException e) {
            logger.warn("JoinWithNodesRequest failed: {} {}", e.getStatus(), e.getMessage());
        }
    }

    private void joinToNetwork(Join joinRequest) {
        logger.info("Sending joinToNetwork to node: {}", serverNetworkAddress);
        try {
            JoinResponse joinResponse = joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .joinToNode(joinRequest);
            instanceHoldingId = UUID.fromString(joinResponse.getServerId());
            logger.info("Successfully joined to node: {}", serverNetworkAddress);
        } catch (StatusRuntimeException e) {
            logger.warn("JoinToNetwork to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public UUID getInstanceHoldingId() {
        return instanceHoldingId;
    }

    public void sendRequest(int lamportClock) {
        logger.info("Sending sendRequest to node: {}", serverNetworkAddress);
        Request request = Request.newBuilder()
                .setLamportClock(lamportClock)
                .setAddress(GRPCServer.networkAddressToAddress(nodeService.getNodeAddress()))
                .build();
        try {
            sharedMutexBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .registerRequest(request);
        } catch (StatusRuntimeException e) {
            logger.warn("sendRequest to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public void sendReply() {
        logger.info("Sending sendReply to node: {}", serverNetworkAddress);
        Reply reply = Reply.newBuilder()
                .setAddress(GRPCServer.networkAddressToAddress(nodeService.getNodeAddress()))
                .build();
        try {
            sharedMutexBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .registerReply(reply);
        } catch (StatusRuntimeException e) {
            logger.warn("sendReply to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public void updateSharedVariable(int sharedVariable) {
        logger.info("Sending updateSharedVariable to node: {}", serverNetworkAddress);
        SharedVariableUpdate sharedVariableUpdate = SharedVariableUpdate.newBuilder()
                .setNewValue(sharedVariable)
                .build();
        try {
            sharedMutexBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .updateSharedVariable(sharedVariableUpdate);
        } catch (StatusRuntimeException e) {
            logger.warn("updateSharedVariable to node {} failed", serverNetworkAddress);
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }
}
