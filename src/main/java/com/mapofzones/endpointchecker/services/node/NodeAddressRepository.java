package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeAddressRepository extends GenericRepository<NodeAddress, String> {

    @Query(value = "SELECT * FROM nodes_addrs zn ORDER BY zn.last_checked_at ASC LIMIT ?1", nativeQuery = true)
    List<NodeAddress> findTopOfOldNodes(Integer limit);

}
