package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("node_info")
    public DefaultNodeInfo nodeInfo;
    @JsonProperty("sync_info")
    public SyncInfo syncInfo;
    @JsonProperty("validator_info")
    public ValidatorInfo validatorInfo;
}
