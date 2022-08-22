package com.mapofzones.endpointchecker.common.constants;

public interface QueryConstants {

    interface NodeAddressQueries {

        String GET_NODE_ADDRESS_BY_TIME_INTERVAL_PARAMETERS =
                "SELECT * FROM nodes_addrs zn " +
                "LEFT JOIN nodes_lcd_addrs lcd on zn.ip_or_dns = lcd.ip_or_dns " +
                "LEFT JOIN nodes_rpc_addrs rpc on zn.ip_or_dns = rpc.ip_or_dns " +
                "WHERE " +
                "(((lcd.last_active is not NULL AND lcd.last_active < :from AND lcd.last_active > :to) " +
                "or (lcd.last_active is NULL AND lcd.added_at < :from AND lcd.added_at > :to)) " +
                "AND lcd.last_checked_at < :timeToCheck) " +
                "OR (((rpc.last_active is not NULL AND rpc.last_active < :from AND rpc.last_active > :to) " +
                "or (rpc.last_active is NULL AND rpc.added_at < :from AND rpc.added_at > :to)) " +
                "AND rpc.last_checked_at < :timeToCheck) " +
                "ORDER BY zn.last_checked_at ASC LIMIT :limit ";

    }

    interface ZoneQueries {
        String FIND_UNIQUE_ZONE_NAMES = "SELECT DISTINCT(z.chainId) FROM Zone z";
    }

}
