package com.AniMy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private Access access;
    private Refresh refresh;

    @Data
    public static class Access {
        private String secret;
        private Long expiration;
    }

    @Data
    public static class Refresh {
        private String secret;
        private Long expiration;
    }
}
