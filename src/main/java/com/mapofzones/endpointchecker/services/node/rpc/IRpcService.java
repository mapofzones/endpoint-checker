package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;

import java.util.Set;

public interface IRpcService {

    void checkLiveness(Node node, Set<String> zoneNames, Status nodeStatus);

}
