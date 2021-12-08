package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultNodeInfoOther {
    @JsonProperty("tx_index")
    public String txIndex;
    @JsonProperty("rpc_address")
    public String rpcAddress;
}
