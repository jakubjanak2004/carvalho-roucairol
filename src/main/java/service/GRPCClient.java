package service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import model.NetworkAddress;
import proto.common.Address;
import proto.connection.AllNetworkNodes;
import proto.connection.Join;
import proto.connection.JoinNetworkGrpc;
import proto.connection.JoinResponse;
import proto.connection.NodeDied;
import proto.sharedMutex.SharedMutexGrpc;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GRPCClient {
    private static final Logger logger = Logger.getLogger(GRPCClient.class.getName());
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
            logger.info(String.format("Successfully pinged node: %s", serverNetworkAddress));
        } catch (StatusRuntimeException e) {
            logger.warning(String.format("ping to node %s failed", serverNetworkAddress));
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public void nodeDiedInfo(NetworkAddress nodeAddress, UUID diedInstanceUUID) {
        logger.info(String.format("Sending nodeDiedInfo to node: %s", serverNetworkAddress));
        NodeDied nodeDied = NodeDied.newBuilder()
                .setServerId(diedInstanceUUID.toString())
                .setAddress(
                        GRPCServer.networkAddressToAddress(nodeAddress)
                ).build();
        try {
            joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .nodeDiedInfo(nodeDied);
        } catch(StatusRuntimeException e) {
            logger.warning(String.format("NodeDiedInfo to node %s failed", serverNetworkAddress));
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    // todo not using right now, employ in the gratefull leaving
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void joinWithNodesRequest(Join joinRequest) {
        logger.info(String.format("Sending joinWithNodesRequest to node: %s", serverNetworkAddress));
        try {
            AllNetworkNodes allNetworkNodes = joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .joinWithNodesRequest(joinRequest);
            instanceHoldingId = UUID.fromString(allNetworkNodes.getReceiverServerId());
            logger.info(String.format("Successfully joined to network: %s", allNetworkNodes));
            for (Join join : allNetworkNodes.getNodesAddressesList()) {
                Address address = join.getAddress();
                nodeService.connectToNode(new NetworkAddress(address.getIpAddress(), address.getPort()), UUID.fromString(join.getServerId()));
            }
        } catch (StatusRuntimeException e) {
            logger.warning("JoinWithNodesRequest failed: " + e.getStatus() + " " + e.getMessage());
            // todo handle when first join to network fails
        }
    }

    private void joinToNetwork(Join joinRequest) {
        logger.info(String.format("Sending joinToNetwork to node: %s", serverNetworkAddress));
        try {
            JoinResponse joinResponse = joinNetworkBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .joinToNode(joinRequest);
            instanceHoldingId = UUID.fromString(joinResponse.getServerId());
            logger.info(String.format("Successfully joined to node: %s", serverNetworkAddress));
        } catch (StatusRuntimeException e) {
            logger.warning(String.format("JoinTpNetwork to node %s failed", serverNetworkAddress));
            nodeService.nodeDied(serverNetworkAddress, instanceHoldingId);
        }
    }

    public UUID getInstanceHoldingId() {
        return instanceHoldingId;
    }
}
