package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncInfo {
    @JsonProperty("latest_block_hash")
    private String LatestBlockHash;
    @JsonProperty("latest_app_hash")
    private String latestAppHash;
    @JsonProperty("latest_block_height")
    private Integer latestBlockHeight;
    @JsonProperty("latest_block_time")
    private Timestamp LatestBlockTime;

    @JsonProperty("earliest_block_hash")
    private String earliestBlockHash;
    @JsonProperty("earliest_app_hash")
    private String earliestAppHash;
    @JsonProperty("earliest_block_height")
    private Integer earliestBlockHeight;
    @JsonProperty("earliest_block_time")
    private Timestamp earliestBlockTime;

    @JsonProperty("catching_up")
    private Boolean catchingUp;

    public String getLatestBlockHash() {
        return LatestBlockHash;
    }

    public String getLatestAppHash() {
        return latestAppHash;
    }

    public Integer getLatestBlockHeight() {
        return latestBlockHeight;
    }

    public Timestamp getLatestBlockTime() {
        return LatestBlockTime;
    }

    public String getEarliestBlockHash() {
        return earliestBlockHash;
    }

    public String getEarliestAppHash() {
        return earliestAppHash;
    }

    public Integer getEarliestBlockHeight() {
        return earliestBlockHeight;
    }

    public Timestamp getEarliestBlockTime() {
        return earliestBlockTime;
    }

    public Boolean getCatchingUp() {
        return catchingUp;
    }
}
