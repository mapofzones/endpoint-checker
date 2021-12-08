package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ValidatorInfo {
    @JsonProperty("address")
    public byte[] address;
//    todo: check it. HashMap type mb change to PubKey custom type
    @JsonProperty("pub_key")
    public HashMap pubKey;
    @JsonProperty("voting_power")
    public Long votingPower;
}
