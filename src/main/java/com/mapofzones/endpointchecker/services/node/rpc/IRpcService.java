package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.Node;

import java.util.Set;

public interface IRpcService {

    void checkLiveness(Node node, Set<String> zoneNames);

}
