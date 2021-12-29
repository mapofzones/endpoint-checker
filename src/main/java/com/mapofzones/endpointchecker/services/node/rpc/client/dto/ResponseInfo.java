package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class ResponseInfo {
    @JsonProperty("data")
    private String data;
    @JsonProperty("version")
    private String version;
    @JsonProperty("app_version")
    private BigInteger appVersion;
    @JsonProperty("last_block_height")
    private Long LastBlockHeight;
    @JsonProperty("last_block_app_hash")
    private String LastBlockAppHash;

    public String getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }

    public BigInteger getAppVersion() {
        return appVersion;
    }

    public Long getLastBlockHeight() {
        return LastBlockHeight;
    }

    public String getLastBlockAppHash() {
        return LastBlockAppHash;
    }
}
