package main;

import controller.CLIController;
import controller.Controller;
import controller.RESTController;
import model.NetworkAddress;
import model.Node;
import service.GRPCServer;
import service.NodeService;
import service.distributedMutex.DistributedMutexService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final List<Controller> controllerList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String nodeName;
        NetworkAddress networkAddress;
        if (args.length != 3 && args.length != 5) {
            throw new IllegalArgumentException("The arguments should be: <node name> <ip address> <port>");
        }
        nodeName = args[0];
        String ipAddress = args[1];
        int port = Integer.parseInt(args[2]);
        networkAddress = new NetworkAddress(ipAddress, port);
        // initialising model
        Node node = new Node(nodeName, networkAddress);
        // initialising services
        NodeService nodeService = new NodeService(node);
        DistributedMutexService distributedMutexService = new DistributedMutexService(nodeService.getOtherNodesMap(), nodeService);
        // initialising and starting gRPC server
        GRPCServer grpcServer = new GRPCServer(node.getMyAddress().port(), nodeService, distributedMutexService);
        grpcServer.start();
        // initialising controllers
        controllerList.add(new CLIController(nodeService, distributedMutexService));
        controllerList.add(new RESTController(nodeService, distributedMutexService));
        controllerList.forEach(Thread::start);
        if (args.length == 5) {
            String otherNodeIpAddress = args[3];
            Integer otherNodePort = Integer.parseInt(args[4]);
            if (!Objects.equals(otherNodeIpAddress, ipAddress) || !Objects.equals(otherNodePort, port)) {
                nodeService.connectToFirstNode(new NetworkAddress(otherNodeIpAddress, otherNodePort));
            }
        }
    }
}
