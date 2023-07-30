package com.sage.tddo.gatewayserver.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Data
public class JwtProperties {
    private String secret;
    private long expirationSecond;
}
