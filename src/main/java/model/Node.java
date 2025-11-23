package model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Node {
    private int sharedVariable = 0;
    private final UUID instanceID;
    private final String nodeName;
    private final NetworkAddress myNetworkAddress;
    private final Set<NetworkAddress> myNeighbors = new HashSet<>();

    public Node(String nodeName, NetworkAddress myNetworkAddress) {
        this.instanceID = UUID.randomUUID();
        this.nodeName = nodeName;
        this.myNetworkAddress = myNetworkAddress;
    }

    public NetworkAddress getMyAddress() {
        return myNetworkAddress;
    }

    public Set<NetworkAddress> getMyNeighbors() {
        return myNeighbors;
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
        if (!myNeighbors.isEmpty()) sb.append(" other nodes:");
        myNeighbors.forEach(neighbour -> {
            sb.append("    ").append(neighbour).append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

    public String getNodeName() {
        return nodeName;
    }

    public UUID getInstanceID() {
        return instanceID;
    }
}
