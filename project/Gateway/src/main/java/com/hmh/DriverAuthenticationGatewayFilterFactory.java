package com.hmh;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class DriverAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<DriverAuthenticationGatewayFilterFactory.Config> {

    private static final String AUTH_HEADER_NAME = "TOKEN";

    public DriverAuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = String.valueOf(request.getPath());
            /** exclude some paths */
            if (requestPath.equals("/register")||requestPath.equals("/login"))
                return chain.filter(exchange);
            // 获取Authorization请求头中的JWT令牌
            String token = request.getHeaders().getFirst(AUTH_HEADER_NAME);
            if (token == null || token.isEmpty()) {
                // 如果请求头中没有Authorization或者Authorization格式不正确，返回401错误响应
                return unauthorized(exchange.getResponse());
            }
            Long id = TokenUtil.checkPassengerToken(token);
            if (id == null) {
                // 如果JWT令牌无效，返回401错误响应
                return unauthorized(exchange.getResponse());
            }

            // 将subject设置到请求属性中
            ServerHttpRequest mutatedRequest = request.mutate().header("id", id.toString()).build();
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

            // 身份验证通过，将请求传递给下一个过滤器
            return chain.filter(mutatedExchange);
        };
    }

    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static class Config {
        // 可以添加一些配置属性，根据需要进行设置
    }
}
