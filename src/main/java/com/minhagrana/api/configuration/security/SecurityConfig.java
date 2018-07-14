package com.minhagrana.api.configuration.security;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    private static Authentication newEmptyAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList((GrantedAuthority) () -> "ADMIN");
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return null;
            }
        };
    }

    @Bean
    JwkProvider jwkProviderBuilder() {
        return new JwkProviderBuilder(issuer).build();
    }

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(JwkProvider jwkProvider) {
        return new JwtAuthenticationProvider(jwkProvider, issuer, apiAudience);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, JwtAuthenticationProvider jwtAuthenticationProvider) {
        return http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                .and().authorizeExchange()
                .pathMatchers("/public/**").permitAll()
                .pathMatchers("/**").authenticated()
                .pathMatchers("/admin/**").hasAuthority("ADMIN")
                .anyExchange().authenticated()
                .and()
                .securityContextRepository(new ServerSecurityContextRepository() {
                    @Override
                    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
                        log.info(">>> serverWebExchange: {}", serverWebExchange);
                        log.info(">>> securityContext: {}", securityContext);
                        return Mono.empty();
                    }

                    @Override
                    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
                        SecurityContext context = SecurityContextHolder.createEmptyContext();

                        tokenFromRequest(serverWebExchange.getRequest())
                                .ifPresent(token ->
                                        Optional.ofNullable(PreAuthenticatedAuthenticationJsonWebToken.usingToken(token))
                                                .ifPresent(context::setAuthentication));

                        if (context.getAuthentication() == null) {
                            context.setAuthentication(newEmptyAuthentication());
                        }

                        return Mono.just(context);
                    }
                })
                .authenticationManager(originalAuth -> {
                    Authentication authentication = originalAuth;
                    Authentication jwtAuthentication = jwtAuthenticationProvider.authenticate(authentication);
                    if (jwtAuthentication != null) {
                        authentication = jwtAuthentication;
                    }
                    return Mono.just(authentication);
                }).build();
    }

    private Optional<String> tokenFromRequest(ServerHttpRequest serverHttpRequest) {
        HttpHeaders headers = serverHttpRequest.getHeaders();
        AtomicReference<String> atomicTokenReference = new AtomicReference<>();
        Optional.ofNullable(headers.get("Authorization"))
                .ifPresent(authorizationHeaders -> authorizationHeaders.stream()
                        .filter(authorizationHeader -> authorizationHeader.toLowerCase().startsWith("bearer"))
                        .findFirst()
                        .ifPresent(bearerHeader -> atomicTokenReference.set(bearerHeader.split(" ")[1].trim())));
        return Optional.ofNullable(atomicTokenReference.get());
    }

}
