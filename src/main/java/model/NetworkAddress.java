package model;

import org.jetbrains.annotations.NotNull;

public record NetworkAddress(String ipAddress, int port) implements Comparable<NetworkAddress>{
    @Override
    public int compareTo(@NotNull NetworkAddress o) {
        // first compare IPs (lexicographically)
        int cmp = this.ipAddress.compareTo(o.ipAddress());
        if (cmp != 0) {
            return cmp;
        }

        // if IPs are equal, compare ports
        return Integer.compare(this.port, o.port());
    }
}
