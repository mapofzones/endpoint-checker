package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public interface NodeInfo {

    @JsonRpcMethod("status")
    Status getStatus() throws SocketTimeoutException, ConnectException;

    @JsonRpcMethod("net_info")
    NetInfo getNetInfo() throws SocketTimeoutException, ConnectException;

    @JsonRpcMethod("abci_info")
    ABCIInfo getABCIInfo() throws SocketTimeoutException, ConnectException;
}
