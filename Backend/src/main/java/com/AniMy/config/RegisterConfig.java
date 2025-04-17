package com.AniMy.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class RegisterConfig {

    private final AuthProviderConfig authProviderConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/registration/**").permitAll() // Allow registration endpoints
                        .anyRequest().authenticated() // All other requests must be authenticated
                )
                .authenticationProvider(authProviderConfig.authenticationProvider())
                .formLogin(withDefaults()); // Enable form login

        return http.build();
    }



}

