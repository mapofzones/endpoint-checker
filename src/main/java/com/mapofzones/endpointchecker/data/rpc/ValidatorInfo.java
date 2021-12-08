package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ValidatorInfo {
    @JsonProperty("address")
    private byte[] address;
//    todo: check it. HashMap type mb change to PubKey custom type
    @JsonProperty("pub_key")
    private HashMap pubKey;
    @JsonProperty("voting_power")
    private Long votingPower;

    public byte[] getAddress() {
        return address;
    }

    public HashMap getPubKey() {
        return pubKey;
    }

    public Long getVotingPower() {
        return votingPower;
    }
}
