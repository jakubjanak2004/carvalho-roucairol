package model;

import service.GRPCClient;

import java.util.concurrent.atomic.AtomicBoolean;

public class OtherNode {
    private final AtomicBoolean hasGranted = new AtomicBoolean(false);
    private final AtomicBoolean isDeferred = new AtomicBoolean(false);
    private final GRPCClient grpcClient;

    public OtherNode(GRPCClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public AtomicBoolean getHasGranted() {
        return hasGranted;
    }

    public AtomicBoolean getIsDeferred() {
        return isDeferred;
    }

    public GRPCClient getGrpcClient() {
        return grpcClient;
    }
}
