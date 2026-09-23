package com.system.api.gateway.config;

import com.system.api.gateway.jwt.JwtService;
import com.system.api.gateway.security.AuthTokenFilter;
import com.system.api.gateway.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            JwtService jwtService,
                                                            UserDetailsServiceImpl userDetailsService) throws Exception {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .addFilterAt(new AuthTokenFilter(jwtService, userDetailsService), SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange(exchange -> exchange
                .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/orders/**").authenticated()
                .anyExchange().permitAll()
            );

        return http.build();
    }
}
