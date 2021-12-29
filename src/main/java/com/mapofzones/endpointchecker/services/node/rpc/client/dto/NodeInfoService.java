package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public interface NodeInfoService {
    //        User createUser(@JsonRpcParam(value="theUserName") String userName, @JsonRpcParam(value="thePassword") String password);
    @JsonRpcMethod("status")
    Status getStatus();

    @JsonRpcMethod("net_info")
    NetInfo getNetInfo() throws InvalidFormatException;

    @JsonRpcMethod("abci_info")
    ABCIInfo getABCIInfo() throws SocketTimeoutException, ConnectException;
}
