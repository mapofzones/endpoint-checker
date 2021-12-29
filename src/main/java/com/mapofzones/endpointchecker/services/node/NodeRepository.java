package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.common.constants.QueryConstants;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends GenericRepository<Node, String> {
    @Query(value = QueryConstants.GET_NODES, nativeQuery = true)
    List<Node> getNodes();
}
