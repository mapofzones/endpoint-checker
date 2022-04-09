package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface  NodeAddressRepository extends GenericRepository<NodeAddress, String> {
    @Query(value = "SELECT * FROM nodes_addrs zn " +
            "JOIN nodes_lcd_addrs lcd on zn.ip_or_dns = lcd.ip_or_dns " +
            "JOIN nodes_rpc_addrs rpc on zn.ip_or_dns = rpc.ip_or_dns " +
            "WHERE lcd.last_active < :from AND lcd.last_active > :to AND lcd.last_checked_at < :timeToCheck " +
            "OR rpc.last_active < :from AND rpc.last_active > :to AND rpc.last_checked_at < :timeToCheck " +
            "ORDER BY zn.last_checked_at ASC LIMIT :limit", nativeQuery = true)
    List<NodeAddress> findTopOfOldNodesByTime(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("timeToCheck") LocalDateTime timeToCheck, @Param("limit") Integer limit);

}
