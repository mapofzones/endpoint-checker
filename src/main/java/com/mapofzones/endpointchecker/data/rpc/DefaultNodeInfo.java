package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultNodeInfo {
    @JsonProperty("protocol_version")
    public ProtocolVersion protocolVersion;
    @JsonProperty("id")
    public String defaultNodeID;
    @JsonProperty("listen_addr")
    public String listenAddr;
    @JsonProperty("network")
    public String network;
    @JsonProperty("version")
    public String version;
    @JsonProperty("channels")
    public byte[] channels;
    @JsonProperty("moniker")
    public String moniker;
    @JsonProperty("other")
    public DefaultNodeInfoOther other;
}
