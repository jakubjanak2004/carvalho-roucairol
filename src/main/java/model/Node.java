package model;

import service.NodeService;

import java.util.UUID;

public class Node {
    private final UUID instanceID;
    private final String nodeName;
    private final NetworkAddress myNetworkAddress;
    private NodeService nodeService;
    private int sharedVariable = 0;

    public Node(String nodeName, NetworkAddress myNetworkAddress) {
        this.instanceID = UUID.randomUUID();
        this.nodeName = nodeName;
        this.myNetworkAddress = myNetworkAddress;
    }

    public NetworkAddress getMyAddress() {
        return myNetworkAddress;
    }

    public int getSharedVariable() {
        return sharedVariable;
    }

    public void setSharedVariable(int sharedVariable) {
        this.sharedVariable = sharedVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(nodeName).append(" ").append(myNetworkAddress);
        if (nodeService != null) {
            if (!nodeService.getOtherNodesMap().isEmpty()) sb.append(" other nodes:");
            nodeService.getOtherNodesMap().forEach((networkAddress, otherNode) -> sb.append("    ").append(networkAddress).append("\n"));
        }
        sb.append("}");
        return sb.toString();
    }

    public String getNodeName() {
        return nodeName;
    }

    public UUID getInstanceID() {
        return instanceID;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
