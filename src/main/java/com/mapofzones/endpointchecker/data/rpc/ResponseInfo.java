package com.mapofzones.endpointchecker.data.rpc;

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
    private byte[] LastBlockAppHash;

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

    public byte[] getLastBlockAppHash() {
        return LastBlockAppHash;
    }
}
