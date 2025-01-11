package com.bluestar.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthProperties {
    public List<String> excludePaths;
    public List<String> IncludePaths;
}
