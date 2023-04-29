package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    @JsonProperty("node_info")
    private DefaultNodeInfo nodeInfo;
    @JsonProperty("sync_info")
    private SyncInfo syncInfo;
    @JsonProperty("validator_info")
    private ValidatorInfo validatorInfo;

    public DefaultNodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public SyncInfo getSyncInfo() {
        return syncInfo;
    }

    public ValidatorInfo getValidatorInfo() {
        return validatorInfo;
    }
}
