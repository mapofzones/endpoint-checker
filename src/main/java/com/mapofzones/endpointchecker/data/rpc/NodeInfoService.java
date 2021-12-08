package com.mapofzones.endpointchecker.data.rpc;

import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.util.LinkedHashMap;

public interface NodeInfoService {
    //        User createUser(@JsonRpcParam(value="theUserName") String userName, @JsonRpcParam(value="thePassword") String password);
    @JsonRpcMethod("status")
    Status getStatus();

    @JsonRpcMethod("net_info")
    NetInfo getNetInfo();

    @JsonRpcMethod("abci_info")
    ABCIInfo getABCIInfo();
}
