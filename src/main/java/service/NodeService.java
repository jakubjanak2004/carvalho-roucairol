package service;

import model.NetworkAddress;
import model.Node;
import model.OtherNode;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NodeService {
    private final Node node;
    private final ConcurrentHashMap<NetworkAddress, OtherNode> otherNodesMap = new ConcurrentHashMap<>();

    public NodeService(Node node) {
        this.node = node;
        node.setNodeService(this);
    }


    public void connectToFirstNode(NetworkAddress networkAddress) {
        GRPCClient grpcClient = new GRPCClient(networkAddress, this);
        OtherNode otherNode = new OtherNode(grpcClient);
        otherNodesMap.put(networkAddress, otherNode);
        grpcClient.joinWithNodesRequest();
    }

    public void connectToNode(NetworkAddress networkAddress, UUID serverId) {
        GRPCClient newClient = new GRPCClient(networkAddress, this, serverId);
        OtherNode otherNode = new OtherNode(newClient);
        otherNodesMap.put(networkAddress, otherNode);
        newClient.joinToNetwork();
    }

    public void nodeDied(NetworkAddress serverNetworkAddress, UUID diedInstanceUUID) {
        otherNodesMap.remove(serverNetworkAddress);
        otherNodesMap.forEach((networkAddress, otherNode) ->
                otherNode.getGrpcClient().nodeDiedInfo(serverNetworkAddress, diedInstanceUUID));
    }

    public int getSharedVariable() {
        return node.getSharedVariable();
    }

    public void setSharedVariable(int sharedVariable) {
        node.setSharedVariable(sharedVariable);
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

    public ConcurrentHashMap<NetworkAddress, OtherNode> getOtherNodesMap() {
        return otherNodesMap;
    }

    public UUID getInstanceId() {
        return node.getInstanceID();
    }
}
