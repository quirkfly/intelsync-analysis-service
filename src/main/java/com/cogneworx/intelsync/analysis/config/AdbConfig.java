package com.cogneworx.intelsync.analysis.config;

import org.springframework.context.annotation.Configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;

@Configuration
@EnableArangoRepositories(basePackages = "com.cogneworx.intelsync")
public class AdbConfig implements ArangoConfiguration {
    private final ArangoProperties properties;

    public AdbConfig(ArangoProperties properties) {
        this.properties = properties;
    }

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder()
                .host(properties.getHost(), properties.getPort())
                .user(properties.getUsername())
                .password(properties.getPassword());
    }

    @Override
    public String database() {
        return properties.getDatabase();
    }

    @Override
    public boolean returnOriginalEntities() {
        return false;
    }
}
