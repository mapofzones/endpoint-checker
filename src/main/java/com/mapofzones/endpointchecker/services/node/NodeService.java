package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.GenericService;
import com.mapofzones.endpointchecker.services.node.lcd.ILcdService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.utils.URLHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class NodeService extends GenericService<Node, String, NodeRepository> implements INodeService {

    private final ILcdService lcdService;
    private final IRpcService rpcService;
    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository repository,
                       ILcdService lcdService,
                       IRpcService rpcService,
                       NodeRepository nodeRepository) {
        super(repository);
        this.lcdService = lcdService;
        this.rpcService = rpcService;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public List<Node> findTopOfOldNodes(Integer limit) {
        return nodeRepository.findTopOfOldNodes(limit);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Set<Node> checkLivenessAndFindPeers(Node node, Set<String> zoneNames) {

        if (URLHelper.isAddressValid(node.getAddress())) {
            Set<Node> foundPeers = rpcService.checkLivenessAndFindPeers(node, zoneNames);
            lcdService.checkLiveness(node);
            return foundPeers;
        } else return Collections.emptySet();
    }
}
