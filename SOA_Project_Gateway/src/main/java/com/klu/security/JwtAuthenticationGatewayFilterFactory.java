package com.klu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            // 1. Check Authorization header
            String authHeader =
                    request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null) {
                return onError(
                        exchange,
                        "Missing Authorization Header",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // 2. Check Bearer format
            if (!authHeader.startsWith("Bearer ")) {
                return onError(
                        exchange,
                        "Invalid Authorization Header Format",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // 3. Extract JWT token
            String token = authHeader.substring(7);

            // 4. Validate JWT
            if (!jwtUtil.isTokenValid(token)) {
                return onError(
                        exchange,
                        "Invalid or Expired JWT Token",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // 5. Extract username and role
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            // 6. Pass authenticated information downstream
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-Auth-User", username)
                    .header("X-Auth-Role", role)
                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(modifiedRequest)
                            .build()
            );
        };
    }

    private Mono<Void> onError(
            ServerWebExchange exchange,
            String message,
            HttpStatus httpStatus) {

        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}