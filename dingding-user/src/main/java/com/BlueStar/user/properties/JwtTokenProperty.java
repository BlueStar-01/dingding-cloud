package com.BlueStar.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenProperty {
    //解码密匙
    private String userSecretKry;
    //过期时间
    private Long userTTL;
    //前端令牌关键词
    private String userTokenName;
}
