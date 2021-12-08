package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NetInfo {
    @JsonProperty("listening")
    private Boolean listening;
    @JsonProperty("listeners")
    private ArrayList<String> listeners;
    @JsonProperty("n_peers")
    private Integer nPeers;
    @JsonProperty("peers")
    private Peer[] peers;

    public Boolean getListening() {
        return listening;
    }

    public ArrayList<String> getListeners() {
        return listeners;
    }

    public Integer getnPeers() {
        return nPeers;
    }

    public Peer[] getPeers() {
        return peers;
    }
}
