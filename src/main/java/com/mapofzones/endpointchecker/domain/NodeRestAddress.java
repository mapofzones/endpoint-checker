package com.mapofzones.endpointchecker.domain;

import com.mapofzones.endpointchecker.common.constants.NodeConstants;
import com.mapofzones.endpointchecker.utils.URLHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "restAddress")
@Entity
@Table(name = "NODES_LCD_ADDRS")
public class NodeRestAddress {

    @Id
    @Column(name = "LCD_ADDR")
    private String restAddress;

    @Column(name = "ZONE")
    @NonNull
    private String zone;

    @Column(name = "IP_OR_DNS")
    private String nodeAddress;

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

    public static NodeRestAddress fromRpcAddress(NodeRpcAddress rpcAddress) {
        String port = URLHelper.getPort(rpcAddress.getRpcAddress());
        String restAddress = rpcAddress.getRpcAddress().replace(":" + port, ":" + NodeConstants.REST_DEFAULT_PORT);

        NodeRestAddress nodeRestAddress = new NodeRestAddress();
        nodeRestAddress.setRestAddress(restAddress);
        nodeRestAddress.setNodeAddress(rpcAddress.getNodeAddress());
        nodeRestAddress.setLastCheckedAt(LocalDateTime.now());
        nodeRestAddress.setLastActive(null);
        nodeRestAddress.setIsAlive(false);
        nodeRestAddress.setZone(rpcAddress.getZone());
        nodeRestAddress.setIsHidden(rpcAddress.getIsHidden());
        nodeRestAddress.setIsPrioritized(rpcAddress.getIsPrioritized());
        nodeRestAddress.setAddedAt(LocalDateTime.now());

        return nodeRestAddress;
    }

    @Override
    public String toString() {
        return "NodeRestAddress{" +
                "restAddress='" + restAddress + '\'' +
                '}';
    }
}
