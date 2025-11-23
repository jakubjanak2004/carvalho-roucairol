package service;

import model.NetworkAddress;
import model.Node;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NodeService {
    private final Node node;
    private final ConcurrentHashMap<NetworkAddress, GRPCClient> otherNodesMap = new ConcurrentHashMap<>();

    public NodeService(Node node) {
        this.node = node;
    }

    public int getSharedVariable() {
        return node.getSharedVariable();
    }

    public String getNodeState() {
        return node.toString();
    }

    public NetworkAddress getNodeAddress() {
        return node.getMyAddress();
    }

    public String getNodeName() {
        return node.getNodeName();
    }

    public ConcurrentHashMap<NetworkAddress, GRPCClient> getOtherNodesMap() {
        return otherNodesMap;
    }

    public void connectToFirstNode(NetworkAddress networkAddress) {
        GRPCClient grpcClient = new GRPCClient(networkAddress, this);
        otherNodesMap.put(networkAddress, grpcClient);
        grpcClient.joinWithNodesRequest();
    }

    public void connectToNode(NetworkAddress networkAddress, UUID serverId) {
        GRPCClient newClient = new GRPCClient(networkAddress, this, serverId);
        otherNodesMap.put(networkAddress, newClient);
        newClient.joinToNetwork();
    }

    public void nodeDied(NetworkAddress serverNetworkAddress, UUID diedInstanceUUID) {
        otherNodesMap.remove(serverNetworkAddress);
        otherNodesMap.forEach((networkAddress, grpcClient) ->
                grpcClient.nodeDiedInfo(serverNetworkAddress, diedInstanceUUID));
    }

    public UUID getInstanceId() {
        return node.getInstanceID();
    }
}
