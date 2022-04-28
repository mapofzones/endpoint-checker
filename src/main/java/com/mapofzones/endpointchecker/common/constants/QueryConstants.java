package com.mapofzones.endpointchecker.common.constants;

public interface QueryConstants {

    interface NodeAddressQueries {

        String GET_NODE_ADDRESS_BY_TIME_INTERVAL_PARAMETERS =
                "SELECT * FROM nodes_addrs zn " +
                "JOIN nodes_lcd_addrs lcd on zn.ip_or_dns = lcd.ip_or_dns " +
                "JOIN nodes_rpc_addrs rpc on zn.ip_or_dns = rpc.ip_or_dns " +
                "WHERE lcd.last_active < :from AND lcd.last_active > :to AND lcd.last_checked_at < :timeToCheck " +
                "OR rpc.last_active < :from AND rpc.last_active > :to AND rpc.last_checked_at < :timeToCheck " +
                "ORDER BY zn.last_checked_at ASC LIMIT :limit";
    }

    interface ZoneQueries {
        String FIND_UNIQUE_ZONE_NAMES = "SELECT DISTINCT(z.chainId) FROM Zone z";
    }

}
