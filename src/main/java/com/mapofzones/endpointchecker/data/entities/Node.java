package com.mapofzones.endpointchecker.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(NodeKey.class)
@Table(name = "zone_nodes", schema = "public")
public class Node {
    @Id
    @Column(name = "zone")
    @NonNull
    private String zone;

    @Id
    @Column(name = "rpc_addr")//, unique = true)
    @NonNull
    private String address;

    @Column(name = "is_alive")
    @NonNull
    private Boolean isAlive;

    @Column(name = "last_checked_at")
    @NonNull
    private Timestamp lastCheckedAt;

    @NonNull
    public String getZone() {
        return zone;
    }

    public void setZone(@NonNull String zone) {
        this.zone = zone;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(@NonNull Boolean alive) {
        isAlive = alive;
    }

    @NonNull
    public Timestamp getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(@NonNull Timestamp lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return zone.equals(node.zone) && address.equals(node.address) && isAlive.equals(node.isAlive) && lastCheckedAt.equals(node.lastCheckedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, address, isAlive, lastCheckedAt);
    }

    @Override
    public String toString() {
        return "Node{" +
                "zone='" + zone + '\'' +
                ", address='" + address + '\'' +
                ", isAlive=" + isAlive +
                ", lastCheckedAt=" + lastCheckedAt +
                '}';
    }
}
