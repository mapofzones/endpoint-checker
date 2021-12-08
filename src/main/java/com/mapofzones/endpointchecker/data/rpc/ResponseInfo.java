package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class ResponseInfo {
    @JsonProperty("data")
    public String data;
    @JsonProperty("version")
    public String version;
    @JsonProperty("app_version")
    public BigInteger appVersion;
    @JsonProperty("last_block_height")
    public Long LastBlockHeight;
    @JsonProperty("last_block_app_hash")
    public byte[] LastBlockAppHash;
}
