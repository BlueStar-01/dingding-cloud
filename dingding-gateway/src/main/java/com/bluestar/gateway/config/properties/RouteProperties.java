package com.bluestar.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nacos.routes")
@Data
public class RouteProperties {
    // 路由配置文件的id和分组
    private final String dataId;
    private final String group;
}
