package com.mapofzones.endpointchecker.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "zones", schema = "public")
public class Zone {
    @Id
    @Column(name = "chain_id")
    @NonNull
    private String chainId;

    @NonNull
    public String getChainId() {
        return chainId;
    }

    public void setChainId(@NonNull String chainId) {
        this.chainId = chainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return chainId.equals(zone.chainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chainId);
    }
}
