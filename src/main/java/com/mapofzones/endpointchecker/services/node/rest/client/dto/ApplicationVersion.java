package com.mapofzones.endpointchecker.services.node.rest.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationVersion {

    @JsonProperty("build_tags")
    private String buildTags;
    @JsonProperty("client_name")
    private String clientName;
    @JsonProperty("commit")
    private String commit;
    @JsonProperty("go")
    private String go;
    @JsonProperty("name")
    private String name;
    @JsonProperty("server_name")
    private String serverName;
    @JsonProperty("version")
    private String version;

}
