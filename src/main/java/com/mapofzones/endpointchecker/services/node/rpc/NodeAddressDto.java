package com.mapofzones.endpointchecker.services.node.rpc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "ipOrDns")
@NoArgsConstructor
public class NodeAddressDto {

    private String ipOrDns;
    private LocalDateTime lastCheckedAt;

}
