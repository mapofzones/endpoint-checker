package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class ProtocolVersion {
    @JsonProperty("p2p")
    public BigInteger p2p;
    @JsonProperty("block")
    public BigInteger block;
    @JsonProperty("app")
    public BigInteger app;
}
