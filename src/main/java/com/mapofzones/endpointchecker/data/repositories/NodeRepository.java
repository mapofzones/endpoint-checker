package com.mapofzones.endpointchecker.data.repositories;

import com.mapofzones.endpointchecker.data.constants.QueryConstants;
import com.mapofzones.endpointchecker.data.entities.Node;
import com.mapofzones.endpointchecker.data.entities.NodeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, NodeKey> {
    @Query(value = QueryConstants.GET_NODES, nativeQuery = true)
    List<Node> getNodes();
}
