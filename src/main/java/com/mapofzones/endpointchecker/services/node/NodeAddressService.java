package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.domain.NodeLcdAddress;
import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.base.GenericService;
import com.mapofzones.endpointchecker.services.node.lcd.ILcdService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.node.rpc.NodeAddressDto;
import com.mapofzones.endpointchecker.utils.URLHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class NodeAddressService extends GenericService<NodeAddress, String, NodeAddressRepository> implements INodeAddressService {

    private final NodeAddressRepository nodeAddressRepository;
    private final ILcdService lcdService;
    private final IRpcService rpcService;

    public NodeAddressService(NodeAddressRepository nodeAddressRepository,
                              ILcdService lcdService,
                              IRpcService rpcService) {
        super(nodeAddressRepository);
        this.nodeAddressRepository = nodeAddressRepository;
        this.lcdService = lcdService;
        this.rpcService = rpcService;
    }

    @Override
    public List<NodeAddress> findTopOfOldNodesByTime(LocalDateTime from, LocalDateTime to, LocalDateTime timeToCheck, Integer limit) {
        return nodeAddressRepository.findTopOfOldNodesByTime(from, to, timeToCheck, limit);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Set<NodeAddress> checkLivenessAndFindPeers(NodeAddress nodeAddress, Set<String> zoneNames) {

        Set<NodeAddress> nodeAddresses = new HashSet<>();
        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();

        if ((nodeAddress.getLcdAddresses() == null || nodeAddress.getLcdAddresses().isEmpty()) && !nodeAddress.getRpcAddresses().isEmpty()) {
            nodeAddress.setLcdAddresses(Stream.of(NodeLcdAddress.fromRpcAddress(Objects.requireNonNull(nodeAddress.getRpcAddresses().stream().findFirst().orElse(null)))).collect(Collectors.toSet()));
        }

        for (NodeRpcAddress nodeRpcAddress : nodeAddress.getRpcAddresses()) {
            if (URLHelper.isAddressValid(nodeRpcAddress.getRpcAddress())) {
                Map<NodeAddressDto, Set<NodeRpcAddress>> currentFoundPeers = rpcService.checkLivenessAndFindPeers(nodeRpcAddress, zoneNames);
                currentFoundPeers.forEach((key, value) -> {
                    if (foundPeers.containsKey(key))
                        foundPeers.get(key).addAll(value);
                    else foundPeers.put(key, value);
                });
            }
        }

        for (Map.Entry<NodeAddressDto, Set<NodeRpcAddress>> entry : foundPeers.entrySet()) {

            NodeAddress newNodeAddress = new NodeAddress(entry.getKey().getIpOrDns());
            if (nodeAddress.equals(newNodeAddress)) {
                nodeAddress.getRpcAddresses().addAll(entry.getValue());
                Set<NodeLcdAddress> lcdAddresses = entry.getValue().stream().map(NodeLcdAddress::fromRpcAddress).collect(Collectors.toSet());
                nodeAddress.getLcdAddresses().addAll(lcdAddresses);
            } else {
                newNodeAddress.setRpcAddresses(entry.getValue());
                Set<NodeLcdAddress> lcdAddresses = entry.getValue().stream().map(NodeLcdAddress::fromRpcAddress).collect(Collectors.toSet());
                newNodeAddress.setLcdAddresses(lcdAddresses);
            }
            nodeAddresses.add(newNodeAddress);
        }

        for (NodeLcdAddress nodeLcdAddress : nodeAddress.getLcdAddresses()) {
            if (URLHelper.isAddressValid(nodeLcdAddress.getLcdAddress())) {
                lcdService.checkLiveness(nodeLcdAddress);
            }
        }

        return nodeAddresses;
    }
}
