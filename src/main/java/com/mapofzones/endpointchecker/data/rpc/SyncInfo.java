package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class SyncInfo {
    @JsonProperty("latest_block_hash")
    private byte[] LatestBlockHash;
    @JsonProperty("latest_app_hash")
    private byte[] latestAppHash;
    @JsonProperty("latest_block_height")
    private Long latestBlockHeight;
    @JsonProperty("latest_block_time")
    private Timestamp LatestBlockTime;

    @JsonProperty("earliest_block_hash")
    private byte[] earliestBlockHash;
    @JsonProperty("earliest_app_hash")
    private byte[] earliestAppHash;
    @JsonProperty("earliest_block_height")
    private Long earliestBlockHeight;
    @JsonProperty("earliest_block_time")
    private Timestamp earliestBlockTime;

    @JsonProperty("catching_up")
    private Boolean catchingUp;

    public byte[] getLatestBlockHash() {
        return LatestBlockHash;
    }

    public byte[] getLatestAppHash() {
        return latestAppHash;
    }

    public Long getLatestBlockHeight() {
        return latestBlockHeight;
    }

    public Timestamp getLatestBlockTime() {
        return LatestBlockTime;
    }

    public byte[] getEarliestBlockHash() {
        return earliestBlockHash;
    }

    public byte[] getEarliestAppHash() {
        return earliestAppHash;
    }

    public Long getEarliestBlockHeight() {
        return earliestBlockHeight;
    }

    public Timestamp getEarliestBlockTime() {
        return earliestBlockTime;
    }

    public Boolean getCatchingUp() {
        return catchingUp;
    }
}
