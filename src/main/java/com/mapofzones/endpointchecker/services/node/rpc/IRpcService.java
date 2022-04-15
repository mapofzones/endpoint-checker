package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.NodeRpcAddress;

import java.util.Map;
import java.util.Set;

public interface IRpcService {

    Map<NodeAddressDto, Set<NodeRpcAddress>> checkLivenessAndFindPeers(NodeRpcAddress nodeRpcAddress, Set<String> zoneNames);

}
