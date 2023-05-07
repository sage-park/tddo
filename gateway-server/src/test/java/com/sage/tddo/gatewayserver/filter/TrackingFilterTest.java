package com.sage.tddo.gatewayserver.filter;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackingFilterTest {

    @Mock
    private GatewayFilterChain chain;

    @Mock
    private FilterUtils filterUtils;

    /**
     * if not exist correlationId, generate one
     */
    @Test
    void testFilter() {

        GlobalFilter filter = new TrackingFilter(filterUtils);
        HttpHeaders headers = new HttpHeaders();
        MockServerHttpRequest request = MockServerHttpRequest
                .get("http://localhost:8080/organizations/e254f8c-c442-4ebe-a82a-e2fc1d1ff78a")
                .headers(headers)
                .build();

        ServerWebExchange exchange = new MockServerWebExchange.Builder(request).build();

        String correlationId = String.valueOf(UUID.randomUUID());
        given(filterUtils.generateCorrelationId()).willReturn(correlationId);

        //when
        filter.filter(exchange, chain);

        //then
        ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);
        then(chain).should().filter(captor.capture());

        assertThat(captor.getValue().getRequest().getHeaders().get(FilterUtils.CORRELATION_ID).get(0))
                .isEqualTo(correlationId);

    }

    /**
     * if exist correlationId , print and pass it
     */
    @Test
    void testFilter2() {

        GlobalFilter filter = new TrackingFilter(filterUtils);
        HttpHeaders headers = new HttpHeaders();
        headers.add(FilterUtils.CORRELATION_ID, "123");
        MockServerHttpRequest request = MockServerHttpRequest
                .get("http://localhost:8080/organizations/e254f8c-c442-4ebe-a82a-e2fc1d1ff78a")
                .headers(headers)
                .build();

        ServerWebExchange exchange = new MockServerWebExchange.Builder(request).build();

        filter.filter(exchange, chain);

        then(chain).should().filter(eq(exchange));
    }

}
