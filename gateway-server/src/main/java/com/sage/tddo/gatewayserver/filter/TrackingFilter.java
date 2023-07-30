package com.sage.tddo.gatewayserver.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TrackingFilter implements GlobalFilter {

    private final FilterUtils filterUtils;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        List<String> strings = headers.get(FilterUtils.CORRELATION_ID);

        String correlationId;

        if (strings == null || strings.isEmpty()) {
            correlationId = filterUtils.generateCorrelationId();
            setCorrelationId(exchange, correlationId);

            //print correlation id
            log.info("correlation-id generated in tracking filter: {}.", correlationId);
        } else {
            //print correlation id
            correlationId = strings.get(0);
            log.info("correlation-id found in tracking filter: {}.", correlationId);
        }

        MDC.put(FilterUtils.CORRELATION_ID, correlationId);

        return chain.filter(exchange);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(name, value)
                                .build())
                .build();
    }


    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, FilterUtils.CORRELATION_ID, correlationId);
    }

}
