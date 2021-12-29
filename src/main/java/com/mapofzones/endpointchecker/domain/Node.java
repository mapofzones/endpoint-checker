package com.mapofzones.endpointchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "address", callSuper = false)
@Entity
@Table(name = "zone_nodes", schema = "public")
public class Node {
    @Column(name = "zone")
    @NonNull
    private String zone;

    @Id
    @Column(name = "rpc_addr")
    @NonNull
    private String address;

    @Column(name = "is_alive")
    @NonNull
    private Boolean isAlive;

    @Column(name = "last_checked_at")
    @NonNull
    private Timestamp lastCheckedAt;

    @Column(name = "ip")
    private String ip;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "version")
    private String version;

    @Column(name = "moniker")
    private String moniker;

    @Column(name = "tx_index")
    private String txIndex;

    @Column(name = "connection_duration")
    private BigInteger connectionDuration;

    @Column(name = "is_send_connection_active")
    private Boolean isSendConnectionActive;

    @Column(name = "is_recv_connection_active")
    private Boolean isRecvConnectionActive;

    @Column(name = "last_block_height")
    private Long lastBlockHeight;

    @Column(name = "is_rpc_addr_active")
    private Boolean isRpcAddrActive;

    @Column(name = "lcd_addr")
    private String lcdAddr;

    @Column(name = "is_lcd_addr_active")
    private Boolean isLcdAddrActive;

    @Column(name = "is_hidden")
    private Boolean isHidden;

    @Column(name = "is_prioritized")
    private Boolean isPrioritized;

    @Column(name = "location_continent")
    private String locationContinent;

    @Column(name = "location_continent_code")
    private String locationContinentCode;

    @Column(name = "location_country")
    private String locationCountry;

    @Column(name = "location_country_code")
    private String location_country_code;

    @Column(name = "location_region")
    private String locationRegion;

    @Column(name = "location_region_name")
    private String locationRegionName;

    @Column(name = "location_city")
    private String locationCity;

    @Column(name = "location_district")
    private String locationDistrict;

    @Column(name = "location_zip")
    private String locationZip;

    @Column(name = "location_lat")
    private Float locationLat;

    @Column(name = "location_lon")
    private Float locationLon;

    @Column(name = "location_timezone")
    private String locationTimezone;

    @Column(name = "location_timezone_offset")
    private Integer locationTimezoneOffset;

    @Column(name = "location_isp_name")
    private String locationIspName;

    @Column(name = "location_org")
    private String locationOrg;

    @Column(name = "location_org_as")
    private String locationOrgAs;

    @Column(name = "location_org_as_name")
    private String locationOrgAsName;

    @Column(name = "is_hosting_location")
    private Boolean isHostingLocation;

    @Override
    public String toString() {
        return "Node{" +
                "zone='" + zone + '\'' +
                ", address='" + address + '\'' +
                ", isAlive=" + isAlive +
                ", lastCheckedAt=" + lastCheckedAt +
                ", ip='" + ip + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", version='" + version + '\'' +
                ", moniker='" + moniker + '\'' +
                ", txIndex='" + txIndex + '\'' +
                ", connectionDuration=" + connectionDuration +
                ", isSendConnectionActive=" + isSendConnectionActive +
                ", isRecvConnectionActive=" + isRecvConnectionActive +
                ", lastBlockHeight=" + lastBlockHeight +
                ", isRpcAddrActive=" + isRpcAddrActive +
                ", lcdAddr='" + lcdAddr + '\'' +
                ", isLcdAddrActive=" + isLcdAddrActive +
                ", isHidden=" + isHidden +
                ", isPrioritized=" + isPrioritized +
                ", locationContinent='" + locationContinent + '\'' +
                ", locationContinentCode='" + locationContinentCode + '\'' +
                ", locationCountry='" + locationCountry + '\'' +
                ", location_country_code='" + location_country_code + '\'' +
                ", locationRegion='" + locationRegion + '\'' +
                ", locationRegionName='" + locationRegionName + '\'' +
                ", locationCity='" + locationCity + '\'' +
                ", locationDistrict='" + locationDistrict + '\'' +
                ", locationZip='" + locationZip + '\'' +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                ", locationTimezone='" + locationTimezone + '\'' +
                ", locationTimezoneOffset=" + locationTimezoneOffset +
                ", locationIspName='" + locationIspName + '\'' +
                ", locationOrg='" + locationOrg + '\'' +
                ", locationOrgAs='" + locationOrgAs + '\'' +
                ", locationOrgAsName='" + locationOrgAsName + '\'' +
                ", isHostingLocation=" + isHostingLocation +
                '}';
    }
}
