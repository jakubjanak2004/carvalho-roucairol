package controller;

import service.NodeService;
import service.distributedMutex.DistributedMutexService;

public abstract class Controller extends Thread {
    protected final NodeService nodeService;
    protected final DistributedMutexService distributedMutexService;
    public Controller(String name, NodeService nodeService, DistributedMutexService distributedMutexService) {
        super(name);
        this.nodeService = nodeService;
        this.distributedMutexService = distributedMutexService;
    }
}
