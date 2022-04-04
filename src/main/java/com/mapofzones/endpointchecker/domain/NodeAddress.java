package com.mapofzones.endpointchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "ipOrDns")
@Table(name = "NODES_ADDRS")
@DynamicInsert
public class NodeAddress implements Cloneable {

    public NodeAddress(@NonNull String ipOrDns) {
        this.ipOrDns = ipOrDns;
        this.lastCheckedAt = LocalDateTime.now();
    }

    @Id
    @Column(name = "IP_OR_DNS")
    @NonNull
    private String ipOrDns;

    @Column(name = "IS_HIDDEN")
    @ColumnDefault("false")
    private Boolean isHidden;

    @Column(name = "IS_PRIORITIZED")
    @ColumnDefault("false")
    private Boolean isPrioritized;

    @Column(name = "CONTINENT")
    private String continent;

    @Column(name = "continent_code")
    private String continentCode;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "REGION")
    private String region;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "LON")
    private String lot;

    @Column(name = "TIMEZONE")
    private String timezone;

    @Column(name = "TIMEZONE_OFFSET")
    private Integer timezoneOffset;

    @Column(name = "ISP_NAME")
    private String ispName;

    @Column(name = "ORG")
    private String org;

    @Column(name = "ORG_AS")
    private String orgAs;

    @Column(name = "ORG_AS_NAME")
    private String orgAsName;

    @Column(name = "IS_HOSTING")
    private Boolean isHosting;

    @Column(name = "LAST_CHECKED_AT")
    @NonNull
    private LocalDateTime lastCheckedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "IP_OR_DNS")
    private Set<NodeLcdAddress> lcdAddresses;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "IP_OR_DNS")
    private Set<NodeRpcAddress> rpcAddresses;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new NodeAddress();
        }
    }

    @Override
    public String toString() {
        return "NodeAddress{" +
                "ipOrDns='" + ipOrDns + '\'' +
                '}';
    }
}
