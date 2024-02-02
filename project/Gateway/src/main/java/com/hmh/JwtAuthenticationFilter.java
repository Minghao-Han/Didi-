package com.hmh;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String JWT_SECRET = "your_jwt_secret";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String jwtToken = extractJwtToken(exchange.getRequest().getHeaders().getFirst("Authorization"));

        if (StringUtils.isEmpty(jwtToken)) {
            LOGGER.error("JWT token is missing or invalid");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            LOGGER.error("JWT token verification failed: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // JWT验证通过，继续处理请求
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String extractJwtToken(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
