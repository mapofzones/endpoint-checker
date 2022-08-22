package com.mapofzones.endpointchecker.services.node.rest;

import com.mapofzones.endpointchecker.domain.NodeRestAddress;

import java.util.Set;

public interface IRestService {

    void checkLiveliness(NodeRestAddress nodeAddress, Set<String> zoneNames);

}
