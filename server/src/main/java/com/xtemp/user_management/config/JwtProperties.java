package com.xtemp.user_management.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
    static public Integer ACCESS_TOKEN_DURATION = 10 * 60 * 1000;
    static public Integer REFRESH_TOKEN_DURATION = 24 * 60 * 60 * 1000;
}