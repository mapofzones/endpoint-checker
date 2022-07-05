package com.mapofzones.endpointchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "rpcAddress")
@Entity
@Table(name = "NODES_RPC_ADDRS")
public class NodeRpcAddress implements Cloneable {

    @Id
    @Column(name = "RPC_ADDR")
    private String rpcAddress;

    @Column(name = "ZONE")
    @NonNull
    private String zone;

    @Column(name = "IP_OR_DNS")
    @NonNull
    private String nodeAddress;

    @Column(name = "NODE_ID")
    private String nodeId;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "MONIKER")
    private String moniker;

    @Column(name = "TX_INDEX")
    private String txIndex;

    @Column(name = "last_block_height")
    private Long lastBlockHeight;

    @Column(name = "earliest_block_height")
    private Long earliestBlockHeight;

    @Column(name = "IS_HIDDEN")
    private Boolean isHidden;

    @Column(name = "IS_PRIORITIZED")
    private Boolean isPrioritized;

    @Column(name = "IS_ALIVE")
    private Boolean isAlive;

    @Column(name = "RESPONSE_TIME")
    private Integer responseTime;

    @Column(name = "LAST_ACTIVE")
    private LocalDateTime lastActive;

    @Column(name = "LAST_CHECKED_AT")
    private LocalDateTime lastCheckedAt;

    @Column(name = "ADDED_AT")
    private LocalDateTime addedAt;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new NodeRpcAddress();
        }
    }

    @Override
    public String toString() {
        return "NodeRpcAddress{" +
                "rpcAddress='" + rpcAddress + '\'' +
                '}';
    }
}
