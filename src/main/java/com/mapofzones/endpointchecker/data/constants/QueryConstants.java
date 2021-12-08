package com.mapofzones.endpointchecker.data.constants;

public interface QueryConstants {
    String GET_NODES = "" +
            "select\n" +
            "    zone,\n" +
            "    rpc_addr,\n" +
            "    is_alive,\n" +
            "    last_checked_at,\n" +
            "    ip,\n" +
            "    node_id,\n" +
            "    version,\n" +
            "    moniker,\n" +
            "    tx_index,\n" +
            "    connection_duration,\n" +
            "    is_send_connection_active,\n" +
            "    is_recv_connection_active,\n" +
            "    last_block_height,\n" +
            "    is_rpc_addr_active,\n" +
            "    lcd_addr,\n" +
            "    is_lcd_addr_active,\n" +
            "    is_hidden,\n" +
            "    is_prioritized,\n" +
            "    location_continent,\n" +
            "    location_continent_code,\n" +
            "    location_country,\n" +
            "    location_country_code,\n" +
            "    location_region,\n" +
            "    location_region_name,\n" +
            "    location_city,\n" +
            "    location_district,\n" +
            "    location_zip,\n" +
            "    location_lat,\n" +
            "    location_lon,\n" +
            "    location_timezone,\n" +
            "    location_timezone_offset,\n" +
            "    location_isp_name,\n" +
            "    location_org,\n" +
            "    location_org_as,\n" +
            "    location_org_as_name,\n" +
            "    is_hosting_location\n" +
            "from\n" +
            "    zone_nodes";
}
