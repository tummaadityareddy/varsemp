package com.varsemp.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity){
        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        httpSecurity.authorizeExchange(ex-> ex.anyExchange().permitAll());
        return httpSecurity.build();
    }
}
