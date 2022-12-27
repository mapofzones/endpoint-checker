package com.mapofzones.endpointchecker.services.node.rpc.client;

import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NodeInfo;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonRpcClientTest {

    @Test
    void initiateRpcClient_ActiveAddress_Test() throws MalformedURLException {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();
        NodeInfo nodeInfo = jsonRpcClient.findNodeInfo("http://65.21.91.99:16957");
        assertAll(() -> {
            assertEquals("osmosis-1", nodeInfo.getStatus().getNodeInfo().getNetwork());
            assertEquals("6f1d92857e39a6f26a3a914f807064824c255939", nodeInfo.getStatus().getNodeInfo().getDefaultNodeID());
        });
    }

    @Test
    void initiateRpcClient_NotActiveAddress_Test() throws MalformedURLException {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();
        NodeInfo nodeInfo = jsonRpcClient.findNodeInfo("http://1.1.1.1:1111");

        assertAll(() -> {
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getStatus()));
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getNetInfo()));
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getABCIInfo()));
        });
    }
}
