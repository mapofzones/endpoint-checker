package com.mapofzones.endpointchecker.services.node;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("singleton")
public class AddressCache {

    private final Set<String> nodeAddressesCache = Collections.synchronizedSet(new HashSet<>());

    public void add(String address) {
        nodeAddressesCache.add(address);
    }

    public void addAll(Set<String> address) {
        nodeAddressesCache.addAll(address);
    }

    public void remove(String address) {
        nodeAddressesCache.remove(address);
    }

    public boolean isExists(String address) {
        return nodeAddressesCache.contains(address);
    }
}
