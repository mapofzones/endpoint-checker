package com.mapofzones.endpointchecker.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class NodeKey implements Serializable {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeKey nodeKey = (NodeKey) o;
        return Objects.equals(address, nodeKey.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
