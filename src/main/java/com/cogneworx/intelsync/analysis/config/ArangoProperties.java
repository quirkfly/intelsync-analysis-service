package com.cogneworx.intelsync.analysis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.arangodb")
@Data
public class ArangoProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String database;
}