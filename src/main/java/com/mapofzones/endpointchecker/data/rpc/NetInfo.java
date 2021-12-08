package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NetInfo {
    @JsonProperty("listening")
    public Boolean listening;
    @JsonProperty("listeners")
    public ArrayList<String> listeners;
    @JsonProperty("n_peers")
    public Integer nPeers;
    @JsonProperty("peers")
    public Peer[] peers;
}
