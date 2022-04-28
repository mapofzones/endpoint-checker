package com.mapofzones.endpointchecker.services.node.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {

    public LocationDto(String status) {
        this.status = status;
    }

    @JsonProperty("status")
    private String status;

    @JsonProperty("continent")
    private String continent;

    @JsonProperty("continentCode")
    private String continentCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("region")
    private String region;

    @JsonProperty("regionName")
    private String regionName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("district")
    private String district;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lon")
    private String lon;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("offset")
    private Integer timezoneOffset;

    @JsonProperty("isp")
    private String isp;

    @JsonProperty("org")
    private String org;

    @JsonProperty("as")
    private String as;

    @JsonProperty("asname")
    private String asName;

    @JsonProperty("hosting")
    private Boolean isHosting;

    @JsonProperty("query")
    private String query;
}