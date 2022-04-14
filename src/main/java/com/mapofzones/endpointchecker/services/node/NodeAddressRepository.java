package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.common.constants.QueryConstants;
import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface  NodeAddressRepository extends GenericRepository<NodeAddress, String> {

    @Query(value = QueryConstants.NodeAddressQueries.GET_NODE_ADDRESS_BY_TIME_INTERVAL_PARAMETERS, nativeQuery = true)
    List<NodeAddress> findTopOfOldNodesByTime(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("timeToCheck") LocalDateTime timeToCheck, @Param("limit") Integer limit);

    List<NodeAddress> findFirst100ByCountryIsNullOrLastCheckedAtBefore(LocalDateTime timeToFind);
}
