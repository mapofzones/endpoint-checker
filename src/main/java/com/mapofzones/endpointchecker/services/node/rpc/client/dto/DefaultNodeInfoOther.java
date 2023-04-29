package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultNodeInfoOther {
    @JsonProperty("tx_index")
    private String txIndex;
    @JsonProperty("rpc_address")
    private String rpcAddress;

    public String getTxIndex() {
        return txIndex;
    }

    public String getRpcAddress() {
        return rpcAddress;
    }
}
