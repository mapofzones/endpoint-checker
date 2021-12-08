package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

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
