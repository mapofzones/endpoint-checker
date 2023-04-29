package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidatorInfo {
    @JsonProperty("address")
    private String address;
//    todo: check it. HashMap type mb change to PubKey custom type
    @JsonProperty("pub_key")
    private HashMap pubKey;
    @JsonProperty("voting_power")
    private Long votingPower;

    public String getAddress() {
        return address;
    }

    public HashMap getPubKey() {
        return pubKey;
    }

    public Long getVotingPower() {
        return votingPower;
    }
}
