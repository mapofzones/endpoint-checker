package com.mapofzones.endpointchecker.services.node.lcd.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationVersion {

    @JsonProperty("build_tags")
    private String buildTags;
    @JsonProperty("client_name")
    private String clientName;
    @JsonAlias({"commit", "git_commit"})
    private String commit;
    @JsonAlias({"go", "go_version"})
    private String go;
    @JsonProperty("name")
    private String name;
    @JsonAlias({"server_name", "app_name"})
    private String serverName;
    @JsonProperty("version")
    private String version;

}
