package main;

import controller.CLIController;
import controller.Controller;
import controller.RESTController;
import model.NetworkAddress;
import model.Node;
import service.GRPCServer;
import service.NodeService;
import service.SharedMutexService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        SharedMutexService sharedMutexService = new SharedMutexService();
        // initialising and starting gRPC server
        GRPCServer grpcServer = new GRPCServer(node.getMyAddress().port(), nodeService);
        grpcServer.start();
        // initialising controllers
        controllerList.add(new CLIController(nodeService, sharedMutexService));
//        controllerList.add(new RESTController(nodeService, sharedMutexService));
        controllerList.forEach(Thread::start);
        if (args.length == 5) {
            // todo set the other node address
            // todo create client and connect to the provided node, then handle other nodes connections
            String otherNodeIpAddress = args[3];
            int otherNodePort = Integer.parseInt(args[4]);
            nodeService.connectToFirstNode(new NetworkAddress(otherNodeIpAddress, otherNodePort));
        }
    }
}
