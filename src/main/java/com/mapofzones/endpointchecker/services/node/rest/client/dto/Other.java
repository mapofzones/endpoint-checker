package com.mapofzones.endpointchecker.services.node.rest.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Other {

    @JsonProperty("tx_index")
    private String txIndex;
    @JsonProperty("rpc_address")
    private String rpcAddress;

}
