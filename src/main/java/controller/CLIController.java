package controller;

import model.NetworkAddress;
import service.NodeService;
import service.distributedMutex.DistributedMutexService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.RejectedExecutionException;

public class CLIController extends Controller {
    private final BufferedReader consoleReader;
    private boolean running = true;

    public CLIController(NodeService nodeService, DistributedMutexService distributedMutexService) {
        super("Command-Line-Controller", nodeService, distributedMutexService);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        NetworkAddress address = nodeService.getNodeAddress();
        String nodeName = nodeService.getNodeName();
        String prompt = nodeName + " " + address.ipAddress() + ":" + address.port();
        while (running) {
            try {
                System.out.printf("%s > ", prompt);
                String command = consoleReader.readLine().trim();
                executeCommand(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executeCommand(String command) {
        if (command.equals("s")) {
            System.out.println(nodeService.getNodeState());
        } else if (command.equals("?") || command.equals("h") || command.equals("help")) {
            printHelp();
        } else if (command.equals("e")) {
            try {
                distributedMutexService.requestCriticalSection();
            } catch (RejectedExecutionException e) {
                System.err.println("Request critical section was Rejected.");
            }
        } else if (command.equals("l")) {
            try {
                distributedMutexService.leaveCriticalSection();
            } catch (RejectedExecutionException e) {
                System.err.println("Leave critical section was Rejected.");
            }
        } else if (command.equals("r")) {
            System.out.println(nodeService.getSharedVariable());
        } else if (command.equals("ping")) {
            nodeService.getOtherNodesMap().forEach((address, otherNode) -> {
                System.out.printf("Sending ping to %s\n", address);
                otherNode.getGrpcClient().ping();
            });
        } else if (command.equals("kill")) {
            System.out.println("Killing node...");
            System.exit(0);
        } else if (!command.isEmpty()) {
            System.out.printf("Unrecognized command: %s\n", command);
            System.out.println("Type '?' for help.");
        }
    }

    private void printHelp() {
        System.out.println("""
                Available commands:
                  s       - Print this node state
                  r       - Read/print shared variable
                  e       - Request entry to critical section
                  l       - Leave critical section
                  ping    - Send ping to all known nodes
                  kill    - Terminate this node process
                  ?       - Show this help (also: h, help)
                """);
    }
}
