package service;

import com.google.protobuf.Empty;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import model.NetworkAddress;
import proto.common.Address;
import proto.connection.AllNetworkNodes;
import proto.connection.Join;
import proto.connection.JoinNetworkGrpc;
import proto.connection.JoinResponse;
import proto.connection.NodeDied;
import proto.sharedMutex.Reply;
import proto.sharedMutex.Request;
import proto.sharedMutex.SharedMutexGrpc;
import proto.sharedMutex.SharedVariableUpdate;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

// todo add a time to server so that when deleting request commes we delete only the old non functional server
public class GRPCServer {
    private static final Logger logger = Logger.getLogger(GRPCServer.class.getName());
    private Server server;
    private final int port;
    private final NodeService nodeService;

    public GRPCServer(int port, NodeService nodeService) {
        this.port = port;
        this.nodeService = nodeService;
    }

    public void start() throws IOException {
        server  = ServerBuilder.forPort(port)
                .addService(new JoinNetworkImpl(nodeService, this))
                .addService(new SharedMutexImpl())
                .build()
                .start();
        logger.info(String.format("gRPC Server started, listening on %d",  port));
    }

    // todo think about moving this
    public static Address networkAddressToAddress(NetworkAddress networkAddress) {
        return Address.newBuilder()
                .setIpAddress(networkAddress.ipAddress())
                .setPort(networkAddress.port())
                .build();
    }

    // todo think about moving this
    public static NetworkAddress addressToNetworkAddress(Address address) {
        return new NetworkAddress(address.getIpAddress(), address.getPort());
    }

    public static String AddressToString(Address address) {
        return "{" + address.getIpAddress() + ":" + address.getPort() + "}";
    }

    static class JoinNetworkImpl extends JoinNetworkGrpc.JoinNetworkImplBase {
        private final NodeService nodeService;
        private final GRPCServer grpcServer;
        JoinNetworkImpl(NodeService nodeService, GRPCServer grpcServer) {
            this.nodeService = nodeService;
            this.grpcServer = grpcServer;
        }

        @Override
        public void joinWithNodesRequest(Join request, StreamObserver<AllNetworkNodes> responseObserver) {
            logger.info(String.format("Node with address %s trying to join network", AddressToString(request.getAddress())));

            // compute the addresses
            List<Join> joinList = nodeService.getOtherNodesMap().entrySet().stream()
                            .map(entry -> Join.newBuilder()
                                    .setServerId(entry.getValue().getInstanceHoldingId().toString())
                                    .setAddress(GRPCServer.networkAddressToAddress(entry.getKey()))
                                    .build()).toList();

            responseObserver.onNext(
                    AllNetworkNodes.newBuilder()
                            .addAllNodesAddresses(joinList)
                            .setReceiverServerId(nodeService.getInstanceId().toString())
                            .build()
            );
            responseObserver.onCompleted();

            // add request address to nodes map
            nodeService.connectToNode(GRPCServer.addressToNetworkAddress(request.getAddress()), nodeService.getInstanceId());
        }

        @Override
        public void joinToNode(Join request, StreamObserver<JoinResponse> responseObserver) {
            logger.info(String.format("Node with address %s trying to join", AddressToString(request.getAddress())));
            responseObserver.onNext(JoinResponse.newBuilder().setServerId(nodeService.getInstanceId().toString()).build());
            responseObserver.onCompleted();
            if (!nodeService.getOtherNodesMap().containsKey(addressToNetworkAddress(request.getAddress()))) {
                nodeService.connectToNode(GRPCServer.addressToNetworkAddress(request.getAddress()), nodeService.getInstanceId());
            }
        }

        @Override
        public void ping(Address request, StreamObserver<Empty> responseObserver) {
            logger.info(String.format("Node with address %s trying to ping", AddressToString(request)));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }

        @Override
        public void nodeDiedInfo(NodeDied request, StreamObserver<Empty> responseObserver) {
            NetworkAddress removedNodeNetworkAddress = GRPCServer.addressToNetworkAddress(request.getAddress());
            logger.info(String.format("Node with address %s died",  removedNodeNetworkAddress));
            nodeService.getOtherNodesMap().computeIfPresent(removedNodeNetworkAddress ,(networkAddress, grpcClient) -> {
                if (grpcClient.getInstanceHoldingId().equals(UUID.fromString(request.getServerId()))) {
                    // remove the client if the ids match
                    return null;
                }
                // leave it there as the UUIDs don't match
                return grpcClient;
            });
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
    }

    static class SharedMutexImpl extends SharedMutexGrpc.SharedMutexImplBase {
        @Override
        public void registerRequest(Request request, StreamObserver<Empty> responseObserver) {
            // todo implement
        }

        @Override
        public void registerReply(Reply request, StreamObserver<Empty> responseObserver) {
            // todo implement
        }

        @Override
        public void updateSharedVariable(SharedVariableUpdate request, StreamObserver<Empty> responseObserver) {
            // todo implement
        }
    }
}
