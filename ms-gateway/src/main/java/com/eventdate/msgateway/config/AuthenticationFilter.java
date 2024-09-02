package com.eventdate.msgateway.config;

import com.eventdate.msgateway.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@AllArgsConstructor
@Slf4j
public class AuthenticationFilter implements GatewayFilter {
    private final RouterValidator validator;
    private final JwtUtils jwtUtils;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return Mono.error(new RuntimeException("Authorization header is missing"));
            }
            String token = getAuthHeader(request);

            return jwtUtils.validateToken(token)
                    .flatMap(isValid -> {
                        if (isValid) {
                            return chain.filter(exchange);
                        } else {
                            return Mono.error(new RuntimeException("Invalid JWT Token"));
                        }
                    });
        }
        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).substring(7);
    }
}
