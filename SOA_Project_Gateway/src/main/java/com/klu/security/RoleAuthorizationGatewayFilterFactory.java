package com.klu.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthorizationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<RoleAuthorizationGatewayFilterFactory.Config> {

    public RoleAuthorizationGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {

        private List<String> roles;

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String userRole =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("X-Auth-Role");

            // No role found
            if (userRole == null || userRole.isBlank()) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // Check whether user's role is allowed
            boolean allowed = config.getRoles()
                    .stream()
                    .anyMatch(role ->
                            role.equalsIgnoreCase(userRole)
                    );

            if (!allowed) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}