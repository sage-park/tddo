package com.sage.tddo.gatewayserver.filter;

import com.sage.tddo.gatewayserver.auth.JsonWebTokenUtils;
import com.sage.tddo.gatewayserver.auth.TokenUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JsonWebTokenUtils jwtUtils;
    private final FilterProperties filterProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("JwtAuthenticationFilter 실행");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        for (String excludedUrl : filterProperties.getExcludedUrls()) {
            String method = excludedUrl.split("-")[0];
            String path = excludedUrl.split("-")[1];

            String actualPath = request.getURI().getPath();
            String actualMethod = request.getMethod().toString();
            if (path.equals(actualPath) && method.equals(actualMethod)) {
                return chain.filter(exchange);
            }
        }

        if (!containsAuthorization(request)) {
            return onError(response, "missing authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = extractToken(request);
        if (!jwtUtils.isValid(token)) {
            return onError(response, "invalid authorization header", HttpStatus.UNAUTHORIZED);
        }

        TokenUser tokenUser = jwtUtils.decode(token);

        addAuthorizationHeaders(request, tokenUser);

        return chain.filter(exchange);
    }

    private void addAuthorizationHeaders(ServerHttpRequest request, TokenUser tokenUser) {
        request.mutate()
                .header("X-Authorization-Id", tokenUser.getUserId())
                .build();
    }

    private String extractToken(ServerHttpRequest request) {
        String header = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
        return header.replace("Bearer ", "");
    }

    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private boolean containsAuthorization(ServerHttpRequest request) {
        return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

}
