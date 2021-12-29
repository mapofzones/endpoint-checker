package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.GenericService;
import org.springframework.stereotype.Service;

@Service
public class NodeService extends GenericService<Node, String, NodeRepository> implements INodeService {

    public NodeService(NodeRepository repository) {
        super(repository);
    }
}
