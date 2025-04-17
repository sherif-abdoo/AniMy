package com.AniMy.config;

import com.AniMy.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@AllArgsConstructor
public class AuthProviderConfig {
    private final UserServices userService;
    private final EncoderConfig encoderConfig;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoderConfig.passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}
