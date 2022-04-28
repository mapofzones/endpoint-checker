package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.IGenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface INodeService extends IGenericService<Node, String, NodeRepository> {

    List<Node> findTopOfOldNodes(Integer limit);
    Set<Node> checkLivenessAndFindPeers(Node node, Set<String> zoneNames);
}
