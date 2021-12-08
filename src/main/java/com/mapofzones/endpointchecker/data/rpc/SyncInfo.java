package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class SyncInfo {
    @JsonProperty("latest_block_hash")
    public byte[] LatestBlockHash;
    @JsonProperty("latest_app_hash")
    public byte[] latestAppHash;
    @JsonProperty("latest_block_height")
    public Long latestBlockHeight;
    @JsonProperty("latest_block_time")
    public Timestamp LatestBlockTime;

    @JsonProperty("earliest_block_hash")
    public byte[] earliestBlockHash;
    @JsonProperty("earliest_app_hash")
    public byte[] earliestAppHash;
    @JsonProperty("earliest_block_height")
    public Long earliestBlockHeight;
    @JsonProperty("earliest_block_time")
    public Timestamp earliestBlockTime;

    @JsonProperty("catching_up")
    public Boolean catchingUp;
}
