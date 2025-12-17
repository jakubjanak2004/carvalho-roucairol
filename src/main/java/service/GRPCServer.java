package service;

import com.google.protobuf.Empty;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
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
import service.distributedMutex.DistributedMutexService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class GRPCServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class.getName());
    private Server server;
    private final int port;
    private final NodeService nodeService;
    private final DistributedMutexService distributedMutexService;

    public GRPCServer(int port, NodeService nodeService, DistributedMutexService distributedMutexService) {
        this.port = port;
        this.nodeService = nodeService;
        this.distributedMutexService = distributedMutexService;
    }

    public void start() throws IOException {
        server  = ServerBuilder.forPort(port)
                .addService(new JoinNetworkImpl(nodeService))
                .addService(new SharedMutexImpl(nodeService, distributedMutexService))
                .build()
                .start();
        logger.info("gRPC Server started, listening on {}",  port);
    }

    public static Address networkAddressToAddress(NetworkAddress networkAddress) {
        return Address.newBuilder()
                .setIpAddress(networkAddress.ipAddress())
                .setPort(networkAddress.port())
                .build();
    }

    public static NetworkAddress addressToNetworkAddress(Address address) {
        return new NetworkAddress(address.getIpAddress(), address.getPort());
    }

    public static String AddressToString(Address address) {
        return "{" + address.getIpAddress() + ":" + address.getPort() + "}";
    }

    static class JoinNetworkImpl extends JoinNetworkGrpc.JoinNetworkImplBase {
        private final NodeService nodeService;

        JoinNetworkImpl(NodeService nodeService) {
            this.nodeService = nodeService;
        }

        @Override
        public void joinWithNodesRequest(Join request, StreamObserver<AllNetworkNodes> responseObserver) {
            logger.info("Node with address {} trying to join network", AddressToString(request.getAddress()));

            // compute the addresses
            List<Join> joinList = nodeService.getOtherNodesMap().entrySet().stream()
                            .map(entry -> Join.newBuilder()
                                    .setServerId(entry.getValue().getGrpcClient().getInstanceHoldingId().toString())
                                    .setAddress(GRPCServer.networkAddressToAddress(entry.getKey()))
                                    .build()).toList();

            responseObserver.onNext(
                    AllNetworkNodes.newBuilder()
                            .addAllNodesAddresses(joinList)
                            .setReceiverServerId(nodeService.getInstanceId().toString())
                            .setSharedVariableUpdate(SharedVariableUpdate.newBuilder()
                                    .setNewValue(nodeService.getSharedVariable())
                                    .build())
                            .build()
            );
            responseObserver.onCompleted();

            // add request address to nodes map
            nodeService.connectToNode(GRPCServer.addressToNetworkAddress(request.getAddress()), nodeService.getInstanceId());
        }

        @Override
        public void joinToNode(Join request, StreamObserver<JoinResponse> responseObserver) {
            logger.info("Node with address {} trying to join", AddressToString(request.getAddress()));
            responseObserver.onNext(JoinResponse.newBuilder().setServerId(nodeService.getInstanceId().toString()).build());
            responseObserver.onCompleted();
            if (!nodeService.getOtherNodesMap().containsKey(addressToNetworkAddress(request.getAddress()))) {
                nodeService.connectToNode(GRPCServer.addressToNetworkAddress(request.getAddress()), nodeService.getInstanceId());
            }
        }

        @Override
        public void ping(Address request, StreamObserver<Empty> responseObserver) {
            logger.info("Node with address {} trying to ping", AddressToString(request));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }

        @Override
        public void nodeDiedInfo(NodeDied request, StreamObserver<Empty> responseObserver) {
            NetworkAddress removedNodeNetworkAddress = GRPCServer.addressToNetworkAddress(request.getAddress());
            logger.info("Node with address {} died",  removedNodeNetworkAddress);
            nodeService.getOtherNodesMap().computeIfPresent(removedNodeNetworkAddress ,(networkAddress, otherNode) -> {
                if (otherNode.getGrpcClient().getInstanceHoldingId().equals(UUID.fromString(request.getServerId()))) {
                    // remove the client if the ids match
                    return null;
                }
                // leave it there as the UUIDs don't match
                return otherNode;
            });
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
    }

    static class SharedMutexImpl extends SharedMutexGrpc.SharedMutexImplBase {
        private final NodeService nodeService;
        private final DistributedMutexService distributedMutexService;

        SharedMutexImpl(NodeService nodeService, DistributedMutexService distributedMutexService) {
            this.nodeService = nodeService;
            this.distributedMutexService = distributedMutexService;
        }

        @Override
        public void registerRequest(Request request, StreamObserver<Empty> responseObserver) {
            logger.info("Received registerRequest from node with address {}", AddressToString(request.getAddress()));
            distributedMutexService.registerRequest(request.getLamportClock(), GRPCServer.addressToNetworkAddress(request.getAddress()));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }

        @Override
        public void registerReply(Reply request, StreamObserver<Empty> responseObserver) {
            logger.info("Received registerReply from node with address {}", AddressToString(request.getAddress()));
            distributedMutexService.registerReply(GRPCServer.addressToNetworkAddress(request.getAddress()));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }

        @Override
        public void updateSharedVariable(SharedVariableUpdate request, StreamObserver<Empty> responseObserver) {
            logger.info("Received updateSharedVariable with new value {}", request.getNewValue());
            nodeService.setSharedVariable(request.getNewValue());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
    }
}
