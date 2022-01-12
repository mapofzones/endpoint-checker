package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends GenericRepository<Node, String> {

    @Query(value = "SELECT * FROM zone_nodes zn ORDER BY zn.last_checked_at ASC LIMIT ?1", nativeQuery = true)
    List<Node> findTopOfOldNodes(Integer limit);

}
