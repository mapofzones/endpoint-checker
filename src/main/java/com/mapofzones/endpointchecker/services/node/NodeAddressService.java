package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.domain.NodeRestAddress;
import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.base.GenericService;
import com.mapofzones.endpointchecker.services.node.rest.IRestService;
import com.mapofzones.endpointchecker.services.node.location.LocationClient;
import com.mapofzones.endpointchecker.services.node.location.LocationDto;
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
    private final IRestService restService;
    private final IRpcService rpcService;
    private final LocationClient locationClient;

    public NodeAddressService(NodeAddressRepository nodeAddressRepository,
                              IRestService restService,
                              IRpcService rpcService,
                              LocationClient locationClient) {
        super(nodeAddressRepository);
        this.nodeAddressRepository = nodeAddressRepository;
        this.restService = restService;
        this.rpcService = rpcService;
        this.locationClient = locationClient;
    }

    @Override
    public List<NodeAddress> findTopOfOldNodesByTime(LocalDateTime from, LocalDateTime to, LocalDateTime timeToCheck, Integer limit) {
        return nodeAddressRepository.findTopOfOldNodesByTime(from, to, timeToCheck, limit);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Set<NodeAddress> checkLivelinessAndFindPeers(NodeAddress nodeAddress, Set<String> zoneNames) {

        Set<NodeAddress> nodeAddresses = new HashSet<>();
        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();

        findAndSetRestFromRpc(nodeAddress);
        checkLivelinessAndFindPeersByRpcAddress(nodeAddress, zoneNames, foundPeers);
        ifNodeAddressNotExistsBuildNewNodeAddress(nodeAddress, foundPeers, nodeAddresses);
        checkLivelinessByRestAddress(nodeAddress, zoneNames);

        return nodeAddresses;
    }

    private void findAndSetRestFromRpc(NodeAddress nodeAddress) {
        if ((nodeAddress.getRestAddresses() == null || nodeAddress.getRestAddresses().isEmpty()) && !nodeAddress.getRpcAddresses().isEmpty()) {
            nodeAddress.setRestAddresses(Stream.of(NodeRestAddress.fromRpcAddress(Objects.requireNonNull(nodeAddress.getRpcAddresses().stream().findFirst().orElse(null)))).collect(Collectors.toSet()));
        }
    }

    private void checkLivelinessAndFindPeersByRpcAddress(NodeAddress nodeAddress, Set<String> zoneNames, Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers) {
        for (NodeRpcAddress nodeRpcAddress : nodeAddress.getRpcAddresses()) {
            if (URLHelper.isAddressValid(nodeRpcAddress.getRpcAddress())) {
                Map<NodeAddressDto, Set<NodeRpcAddress>> currentFoundPeers = rpcService.checkLivelinessAndFindPeers(nodeRpcAddress, zoneNames);
                currentFoundPeers.forEach((key, value) -> {
                    if (foundPeers.containsKey(key))
                        foundPeers.get(key).addAll(value);
                    else foundPeers.put(key, value);
                });
            }
        }
    }

    private void ifNodeAddressNotExistsBuildNewNodeAddress(NodeAddress nodeAddress, Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers, Set<NodeAddress> nodeAddresses) {
        for (Map.Entry<NodeAddressDto, Set<NodeRpcAddress>> entry : foundPeers.entrySet()) {

            NodeAddress newNodeAddress = new NodeAddress(entry.getKey().getIpOrDns());
            if (nodeAddress.equals(newNodeAddress)) {
                nodeAddress.getRpcAddresses().addAll(entry.getValue());
                Set<NodeRestAddress> restAddresses = entry.getValue().stream().map(NodeRestAddress::fromRpcAddress).collect(Collectors.toSet());
                nodeAddress.getRestAddresses().addAll(restAddresses);
            } else {
                newNodeAddress.setRpcAddresses(entry.getValue());
                Set<NodeRestAddress> restAddresses = entry.getValue().stream().map(NodeRestAddress::fromRpcAddress).collect(Collectors.toSet());
                newNodeAddress.setRestAddresses(restAddresses);
            }
            nodeAddresses.add(newNodeAddress);
        }
    }

    private void checkLivelinessByRestAddress(NodeAddress nodeAddress, Set<String> zoneNames) {
        for (NodeRestAddress nodeRestAddress : nodeAddress.getRestAddresses()) {
            if (URLHelper.isAddressValid(nodeRestAddress.getRestAddress())) {
                restService.checkLiveliness(nodeRestAddress, zoneNames);
            }
        }
    }

    @Override
    @Transactional
    public void findLocations(LocalDateTime timeToCheck) {
        List<NodeAddress> nodeAddresses = nodeAddressRepository.findFirst100ByCountryIsNullOrLastCheckedAtBefore(timeToCheck);
        for (NodeAddress nodeAddress : nodeAddresses) {
            LocationDto locationDto = locationClient.findLocation(nodeAddress.getIpOrDns());
            if (locationDto.getStatus().equals("success")) {
                nodeAddress.fillNodeAddress(locationDto);
            }
            nodeAddress.setLastCheckedAt(LocalDateTime.now());
        }
    }
}
