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
@EqualsAndHashCode(of = "lcdAddress")
@Entity
@Table(name = "NODES_LCD_ADDRS")
public class NodeLcdAddress {

    @Id
    @Column(name = "LCD_ADDR")
    private String lcdAddress;

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
    private Long responseTime;

    @Column(name = "LAST_ACTIVE")
    private LocalDateTime lastActive;

    @Column(name = "LAST_CHECKED_AT")
    private LocalDateTime lastCheckedAt;

    @Column(name = "ADDED_AT")
    private LocalDateTime addedAt;

    public static NodeLcdAddress fromRpcAddress(NodeRpcAddress rpcAddress) {
        String port = URLHelper.getPort(rpcAddress.getRpcAddress());
        String lcdAddress = rpcAddress.getRpcAddress().replace(":" + port, ":" + NodeConstants.LCD_DEFAULT_PORT);

        NodeLcdAddress nodeLcdAddress = new NodeLcdAddress();
        nodeLcdAddress.setLcdAddress(lcdAddress);
        nodeLcdAddress.setNodeAddress(rpcAddress.getNodeAddress());
        nodeLcdAddress.setLastCheckedAt(LocalDateTime.now());
        nodeLcdAddress.setLastActive(null);
        nodeLcdAddress.setIsAlive(false);
        nodeLcdAddress.setZone(rpcAddress.getZone());
        nodeLcdAddress.setIsHidden(rpcAddress.getIsHidden());
        nodeLcdAddress.setIsPrioritized(rpcAddress.getIsPrioritized());
        nodeLcdAddress.setAddedAt(LocalDateTime.now());

        return nodeLcdAddress;
    }

    @Override
    public String toString() {
        return "NodeLcdAddress{" +
                "lcdAddress='" + lcdAddress + '\'' +
                '}';
    }
}
