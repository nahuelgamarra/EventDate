package com.eventdate.msreservationservice.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {

    public Mono<Map<String, Object>> extractClaims(String jwtToken) {
        return Mono.justOrEmpty(jwtToken)
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(this::getJwt)
                .map(this::decodePayload)
                .flatMap(this::parseClaims)
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid token")));
    }

    public String getJwt(String jwtToken) {
        return jwtToken.substring(7);
    }

    private String decodePayload(String jwt) {
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new RuntimeException("JWT token is not in the correct format");
        }
        return new String(Base64.getDecoder().decode(parts[1]));
    }

    private Mono<Map<String, Object>> parseClaims(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Mono.just(objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            }));
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Error processing JWT token", e));
        }
    }

    public Mono<Long> getUserId(String jwtToken) {
        return extractClaims(jwtToken)
                .map(claims -> {
                    Object userId = claims.get("userId");
                    if (userId instanceof Integer) {
                        return ((Integer) userId).longValue();
                    } else if (userId instanceof Long) {
                        return (Long) userId;
                    } else {
                        throw new RuntimeException("Invalid userId type in JWT");
                    }
                });
    }
}
