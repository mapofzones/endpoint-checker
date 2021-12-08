package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultNodeInfo {
    @JsonProperty("protocol_version")
    private ProtocolVersion protocolVersion;
    @JsonProperty("id")
    private String defaultNodeID;
    @JsonProperty("listen_addr")
    private String listenAddr;
    @JsonProperty("network")
    private String network;
    @JsonProperty("version")
    private String version;
    @JsonProperty("channels")
    private byte[] channels;
    @JsonProperty("moniker")
    private String moniker;
    @JsonProperty("other")
    private DefaultNodeInfoOther other;

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public String getDefaultNodeID() {
        return defaultNodeID;
    }

    public String getListenAddr() {
        return listenAddr;
    }

    public String getNetwork() {
        return network;
    }

    public String getVersion() {
        return version;
    }

    public byte[] getChannels() {
        return channels;
    }

    public String getMoniker() {
        return moniker;
    }

    public DefaultNodeInfoOther getOther() {
        return other;
    }
}
