package com.eventdate.msgateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/catalog/**")
                        .uri("lb://ms-event-catalog-service")
                )
                .route(p -> p
                        .path("/api/v1/auth/**")
                        .uri("lb://ms-user-service")
                )
                .route(p -> p
                        .path("/api/v1/reservation/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://ms-reservation-service")
                )
                .build();
    }

}
