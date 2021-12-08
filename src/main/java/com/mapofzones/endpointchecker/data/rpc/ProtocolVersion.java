package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class ProtocolVersion {
    @JsonProperty("p2p")
    private BigInteger p2p;
    @JsonProperty("block")
    private BigInteger block;
    @JsonProperty("app")
    private BigInteger app;

    public BigInteger getP2p() {
        return p2p;
    }

    public BigInteger getBlock() {
        return block;
    }

    public BigInteger getApp() {
        return app;
    }
}
