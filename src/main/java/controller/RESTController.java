package controller;

import io.javalin.Javalin;
import model.NetworkAddress;
import service.NodeService;
import service.distributedMutex.DistributedMutexService;

public class RESTController extends Controller {
    private Javalin app;
    public RESTController(NodeService nodeService, DistributedMutexService distributedMutexService) {
        super("REST-Controller", nodeService, distributedMutexService);
    }

    @Override
    public void run() {
        app = Javalin.create()
                .get("/setDelay", ctx -> {
                    // todo set the delay
                })
                .get("/join/{other_node_ip_address}/{other_node_port}", ctx -> {
                    // join to the first node
                    String ipAddress = ctx.pathParam("other_node_ip_address");
                    int port = Integer.parseInt(ctx.pathParam("other_node_port"));
                    nodeService.connectToFirstNode(new NetworkAddress(ipAddress, port));
                })
                .get("/leave", ctx -> {
                    // leave gratefully
                    ctx.result(String.format("Node %s leaving gratefully", nodeService.getNodeName()));
                    // todo implement
                })
                .get("/revive", ctx -> {
                    // todo implement
                })
                .get("/kill", ctx -> System.exit(0))
                .get("/enterCS", ctx -> {
                    ctx.result(String.format("Entering CS on node %s", nodeService.getNodeName()));
                    distributedMutexService.requestCriticalSection();
                })
                .get("/leaveCS", ctx -> {
                    ctx.result(String.format("Leaving CS on node %s", nodeService.getNodeName()));
                    distributedMutexService.leaveCriticalSection();
                })
                .get("/readCS", ctx -> {
                     int sharedVariable = nodeService.getSharedVariable();
                    ctx.result(String.format("Shared variable: %d", sharedVariable));
                })
                .start(nodeService.getNodeAddress().port() + 1);
    }
}
