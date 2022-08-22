package com.mapofzones.endpointchecker.services.node.rpc.client;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NodeInfo;

import java.net.MalformedURLException;
import java.net.URL;

public class JsonRpcClient {

    public NodeInfo findNodeInfo(String url) throws MalformedURLException {
        URL rpcUrl = new URL(url);
        JsonRpcHttpClient client = new JsonRpcHttpClient(rpcUrl);
        client.setConnectionTimeoutMillis(5000);
        client.setReadTimeoutMillis(5000);
        return findNodeInfo(client);
    }

    private NodeInfo findNodeInfo(JsonRpcHttpClient client) {
        return ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                NodeInfo.class,
                client);
    }
}
