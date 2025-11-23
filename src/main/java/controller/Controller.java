package controller;

import service.NodeService;
import service.SharedMutexService;

public abstract class Controller extends Thread {
    protected final NodeService nodeService;
    protected final SharedMutexService sharedMutexService;
    public Controller(String name, NodeService nodeService, SharedMutexService sharedMutexService) {
        super(name);
        this.nodeService = nodeService;
        this.sharedMutexService = sharedMutexService;
    }
}
