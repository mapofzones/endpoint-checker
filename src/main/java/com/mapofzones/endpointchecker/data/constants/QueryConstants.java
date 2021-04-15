package com.mapofzones.endpointchecker.data.constants;

public interface QueryConstants {
    String GET_NODES = "" +
            "select\n" +
            "    zone,\n" +
            "    rpc_addr,\n" +
            "    is_alive,\n" +
            "    last_checked_at\n" +
            "from\n" +
            "    zone_nodes";
}
