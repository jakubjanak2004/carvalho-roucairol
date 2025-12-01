package controller;

import model.NetworkAddress;
import service.NodeService;
import service.sharedMutex.SharedMutexService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.RejectedExecutionException;

public class CLIController extends Controller {
    private final BufferedReader consoleReader;
    // todo handle when to turn off the running
    private boolean running = true;

    public CLIController(NodeService nodeService, SharedMutexService sharedMutexService) {
        super("Command-Line-Controller", nodeService, sharedMutexService);
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
        } else if (command.equals("?")) {
            // todo implement helping
            System.out.println("IMPLEMENT HELP...");
        } else if (command.equals("e")) {
            try {
                sharedMutexService.requestCriticalSection();
            } catch (RejectedExecutionException e) {
                System.err.println("Request critical section was Rejected.");
            }
        } else if (command.equals("l")) {
            try {
                sharedMutexService.leaveCriticalSection();
            } catch (RejectedExecutionException e) {
                System.err.println("Leave critical section was Rejected.");
            }
        } else if (command.equals("r")) {
            System.out.println(nodeService.getSharedVariable());
        } else if(command.equals("ping")) {
            nodeService.getOtherNodesMap().forEach((address, otherNode) -> {
                System.out.printf("Sending ping to %s\n", address);
                otherNode.getGrpcClient().ping();
            });
        } else if(command.equals("kill")) {
            System.out.println("Killing node...");
            System.exit(0);
        }else if (!command.isEmpty()) {
            System.out.printf("Unrecognized command:%s\n", command);
        }
    }
}
