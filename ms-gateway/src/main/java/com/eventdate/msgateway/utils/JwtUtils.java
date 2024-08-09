package com.eventdate.msgateway.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;

@Service
@Slf4j
public class JwtUtils {

    private final String SECRET_KEY = "your-very-long-secret-key-that-is-at-least-32-bytes";

    private Key key;
    @PostConstruct
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Mono<Boolean> validateToken(String token){
        return Mono.fromCallable(()->{
            try {
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);
                log.info("Token validation");
                return true;
            }catch (ExpiredJwtException exp) {
                log.error("Jwt token expired");
                return false;
            }
        });
    }
}
