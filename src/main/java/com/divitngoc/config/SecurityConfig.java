package com.divitngoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        var user = User.withUsername("user")
                       .password(bCryptPasswordEncoder.encode("pass"))
                       .roles("USER")
                       .build();
        var admin = User.withUsername("admin")
                        .password(bCryptPasswordEncoder.encode("pass"))
                        .roles("USER", "ADMIN")
                        .build();
        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                   .httpBasic(Customizer.withDefaults())
                   .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                   .authorizeExchange(authorizeExchangeSpec())
                   .build();
    }

    private Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> authorizeExchangeSpec() {
        return authorizeExchangeSpec ->
                authorizeExchangeSpec.pathMatchers("/facts/**").hasRole("USER")
                                     .anyExchange().authenticated();
    }
}