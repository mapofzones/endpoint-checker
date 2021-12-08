package com.mapofzones.endpointchecker.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(NodeKey.class)
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
    private Integer lastBlockHeight;

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


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public String getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(String txIndex) {
        this.txIndex = txIndex;
    }

    public BigInteger getConnectionDuration() {
        return connectionDuration;
    }

    public void setConnectionDuration(BigInteger connectionDuration) {
        this.connectionDuration = connectionDuration;
    }

    public Boolean getSendConnectionActive() {
        return isSendConnectionActive;
    }

    public void setSendConnectionActive(Boolean sendConnectionActive) {
        isSendConnectionActive = sendConnectionActive;
    }

    public Boolean getRecvConnectionActive() {
        return isRecvConnectionActive;
    }

    public void setRecvConnectionActive(Boolean recvConnectionActive) {
        isRecvConnectionActive = recvConnectionActive;
    }

    public Integer getLastBlockHeight() {
        return lastBlockHeight;
    }

    public void setLastBlockHeight(Integer lastBlockHeight) {
        this.lastBlockHeight = lastBlockHeight;
    }

    public Boolean getRpcAddrActive() {
        return isRpcAddrActive;
    }

    public void setRpcAddrActive(Boolean rpcAddrActive) {
        isRpcAddrActive = rpcAddrActive;
    }

    public String getLcdAddr() {
        return lcdAddr;
    }

    public void setLcdAddr(String lcdAddr) {
        this.lcdAddr = lcdAddr;
    }

    public Boolean getLcdAddrActive() {
        return isLcdAddrActive;
    }

    public void setLcdAddrActive(Boolean lcdAddrActive) {
        isLcdAddrActive = lcdAddrActive;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Boolean getPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(Boolean prioritized) {
        isPrioritized = prioritized;
    }

    public String getLocationContinent() {
        return locationContinent;
    }

    public void setLocationContinent(String locationContinent) {
        this.locationContinent = locationContinent;
    }

    public String getLocationContinentCode() {
        return locationContinentCode;
    }

    public void setLocationContinentCode(String locationContinentCode) {
        this.locationContinentCode = locationContinentCode;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public String getLocation_country_code() {
        return location_country_code;
    }

    public void setLocation_country_code(String location_country_code) {
        this.location_country_code = location_country_code;
    }

    public String getLocationRegion() {
        return locationRegion;
    }

    public void setLocationRegion(String locationRegion) {
        this.locationRegion = locationRegion;
    }

    public String getLocationRegionName() {
        return locationRegionName;
    }

    public void setLocationRegionName(String locationRegionName) {
        this.locationRegionName = locationRegionName;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationDistrict() {
        return locationDistrict;
    }

    public void setLocationDistrict(String locationDistrict) {
        this.locationDistrict = locationDistrict;
    }

    public String getLocationZip() {
        return locationZip;
    }

    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
    }

    public Float getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Float locationLat) {
        this.locationLat = locationLat;
    }

    public Float getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Float locationLon) {
        this.locationLon = locationLon;
    }

    public String getLocationTimezone() {
        return locationTimezone;
    }

    public void setLocationTimezone(String locationTimezone) {
        this.locationTimezone = locationTimezone;
    }

    public Integer getLocationTimezoneOffset() {
        return locationTimezoneOffset;
    }

    public void setLocationTimezoneOffset(Integer locationTimezoneOffset) {
        this.locationTimezoneOffset = locationTimezoneOffset;
    }

    public String getLocationIspName() {
        return locationIspName;
    }

    public void setLocationIspName(String locationIspName) {
        this.locationIspName = locationIspName;
    }

    public String getLocationOrg() {
        return locationOrg;
    }

    public void setLocationOrg(String locationOrg) {
        this.locationOrg = locationOrg;
    }

    public String getLocationOrgAs() {
        return locationOrgAs;
    }

    public void setLocationOrgAs(String locationOrgAs) {
        this.locationOrgAs = locationOrgAs;
    }

    public String getLocationOrgAsName() {
        return locationOrgAsName;
    }

    public void setLocationOrgAsName(String locationOrgAsName) {
        this.locationOrgAsName = locationOrgAsName;
    }

    public Boolean getHostingLocation() {
        return isHostingLocation;
    }

    public void setHostingLocation(Boolean hostingLocation) {
        isHostingLocation = hostingLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return zone.equals(node.zone) && address.equals(node.address) && isAlive.equals(node.isAlive) &&
                lastCheckedAt.equals(node.lastCheckedAt) && Objects.equals(ip, node.ip) &&
                Objects.equals(nodeId, node.nodeId) && Objects.equals(version, node.version) &&
                Objects.equals(moniker, node.moniker) && Objects.equals(txIndex, node.txIndex) &&
                Objects.equals(connectionDuration, node.connectionDuration) &&
                Objects.equals(isSendConnectionActive, node.isSendConnectionActive) &&
                Objects.equals(isRecvConnectionActive, node.isRecvConnectionActive) &&
                Objects.equals(lastBlockHeight, node.lastBlockHeight) &&
                Objects.equals(isRpcAddrActive, node.isRpcAddrActive) &&
                Objects.equals(lcdAddr, node.lcdAddr) && Objects.equals(isLcdAddrActive, node.isLcdAddrActive) &&
                Objects.equals(isHidden, node.isHidden) && Objects.equals(isPrioritized, node.isPrioritized) &&
                Objects.equals(locationContinent, node.locationContinent) &&
                Objects.equals(locationContinentCode, node.locationContinentCode) &&
                Objects.equals(locationCountry, node.locationCountry) &&
                Objects.equals(location_country_code, node.location_country_code) &&
                Objects.equals(locationRegion, node.locationRegion) &&
                Objects.equals(locationRegionName, node.locationRegionName) &&
                Objects.equals(locationCity, node.locationCity) &&
                Objects.equals(locationDistrict, node.locationDistrict) && Objects.equals(locationZip, node.locationZip) &&
                Objects.equals(locationLat, node.locationLat) && Objects.equals(locationLon, node.locationLon) &&
                Objects.equals(locationTimezone, node.locationTimezone) &&
                Objects.equals(locationTimezoneOffset, node.locationTimezoneOffset) &&
                Objects.equals(locationIspName, node.locationIspName) &&
                Objects.equals(locationOrg, node.locationOrg) && Objects.equals(locationOrgAs, node.locationOrgAs) &&
                Objects.equals(locationOrgAsName, node.locationOrgAsName) &&
                Objects.equals(isHostingLocation, node.isHostingLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, address, isAlive, lastCheckedAt, ip, nodeId, version, moniker, txIndex,
                connectionDuration, isSendConnectionActive, isRecvConnectionActive, lastBlockHeight, isRpcAddrActive,
                lcdAddr, isLcdAddrActive, isHidden, isPrioritized, locationContinent, locationContinentCode,
                locationCountry, location_country_code, locationRegion, locationRegionName, locationCity,
                locationDistrict, locationZip, locationLat, locationLon, locationTimezone, locationTimezoneOffset,
                locationIspName, locationOrg, locationOrgAs, locationOrgAsName, isHostingLocation);
    }

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
