package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.IGenericService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface INodeService extends IGenericService<Node, String, NodeRepository> {

    Set<Node> checkLivenessAndFindPeers(Node node, Set<String> zoneNames);
}
