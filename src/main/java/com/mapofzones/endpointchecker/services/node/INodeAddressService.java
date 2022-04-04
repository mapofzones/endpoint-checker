package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.services.base.IGenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface INodeAddressService extends IGenericService<NodeAddress, String, NodeAddressRepository> {

    List<NodeAddress> findTopOfOldNodes(Integer limit);
    Set<NodeAddress> checkLivenessAndFindPeers(NodeAddress nodeAddress, Set<String> zoneNames);
}
