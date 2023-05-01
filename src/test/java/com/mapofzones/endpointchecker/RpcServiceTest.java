package com.mapofzones.endpointchecker;

import com.mapofzones.endpointchecker.config.EndpointCheckerConf;
import com.mapofzones.endpointchecker.config.EndpointCheckerTestConf;
import com.mapofzones.endpointchecker.config.LcdClientConf;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.node.rpc.RpcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@AutoConfigureWebClient
@ContextConfiguration(classes = {EndpointCheckerTestConf.class})
public class RpcServiceTest extends AbstractTest {

    @Autowired
    @Qualifier("rpcService")
    private IRpcService rpcService;

    @Test
    void checkRpcTest() {

        Node node = new Node();
        node.setZone("juno-1");
        node.setAddress("https://rpc-juno.itastakers.com:443");
        node.setIsAlive(false);
        node.setLastCheckedAt(Timestamp.valueOf("2023-04-27 17:23:46.524000"));
        node.setNodeId("4a0991be53ff3d42f0cd9e4c7fc3eed3d7584ce8");
        node.setVersion("v0.34.26");
        node.setMoniker("rpc-6");
        node.setTxIndex("on");
        node.setLastBlockHeight(7875291);
        node.setIsRpcAddrActive(false);
        node.setIsLcdAddrActive(false);
        node.setIsHidden(false);
        node.setIsPrioritized(false);
        node.setIp("rpc-juno.itastakers.com");
        node.setEarliestBlockHeight(4923001);
        node.setLastCheckedAt(Timestamp.valueOf("2022-02-08 16:57:32.959376"));

        Set<String> zoneNames = new HashSet<>();
        zoneNames.add("juno-1");

        rpcService.checkLivenessAndFindPeers(node,zoneNames);

    }

}
