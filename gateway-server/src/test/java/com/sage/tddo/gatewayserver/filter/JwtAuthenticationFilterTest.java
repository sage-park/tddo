package com.sage.tddo.gatewayserver.filter;

import com.sage.tddo.gatewayserver.auth.JsonWebTokenUtils;
import com.sage.tddo.gatewayserver.auth.TokenUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    JsonWebTokenUtils jwtUtils;

    GlobalFilter filter;

    List<String > excludedUrls = List.of("GET-/excludedUrl01", "POST-/excludedUrl02");

    @Mock
    ServerWebExchange exchange;
    @Mock
    GatewayFilterChain chain;
    @Mock
    ServerHttpRequest mockRequest;
    @Spy
    ServerHttpResponse mockResponse = new MockServerHttpResponse();
    @Mock
    private FilterProperties filterProperties;


    @BeforeEach
    void setUp() {
        given(filterProperties.getExcludedUrls()).willReturn(excludedUrls);
        filter = new JwtAuthenticationFilter(jwtUtils, filterProperties);
        given(exchange.getRequest()).willReturn(mockRequest);
        given(exchange.getResponse()).willReturn(mockResponse);
    }


    @DisplayName("만약 config.excludedUrls 에 포함된 url과 method라면 그냥 패스한다.")
    @Test
    void passWhenExcludedUrlTest() throws URISyntaxException {
        passWhenExcludedUrl(HttpMethod.GET, "/excludedUrl01");
        passWhenExcludedUrl(HttpMethod.POST, "/excludedUrl02");

        then(chain).should(times(2)).filter(exchange);
    }

    private void passWhenExcludedUrl(HttpMethod method, String excludedUrl) throws URISyntaxException {
        setCurrentUrl(method, excludedUrl);

        //when
        filter.filter(exchange, chain);

        //then
    }

    private void setCurrentUrl(HttpMethod method, String excludedUrl) throws URISyntaxException {
        given(mockRequest.getMethod()).willReturn(method);
        given(mockRequest.getURI()).willReturn(new URI(excludedUrl));
    }

    @DisplayName("만약 excludedUrl 이 아니고")
    @Nested
    class ifNotExcludedUrl {
        @BeforeEach
        void setup() throws URISyntaxException {
            setCurrentUrl(HttpMethod.GET, "/notExcludedUrl");
        }

        @DisplayName("헤더에 Authorization 이 없다면 401을 반환한다.")
        @Test
        void ifNoAuthorizationHeader() {
            //given
            given(mockRequest.getHeaders()).willReturn(new HttpHeaders());

            //when
            Mono<Void> mono = filter.filter(exchange, chain);

            //then
            assertThat(mockResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        }

        @DisplayName("만약 헤더에 Authorization 이 있으며")
        @Nested
        class existAuthHeader {

            @BeforeEach
            void setup() {
                HttpHeaders headers = new HttpHeaders();
                headers.put(HttpHeaders.AUTHORIZATION, List.of("token"));
                given(mockRequest.getHeaders()).willReturn(headers);
            }

            @DisplayName("토큰이 유효하지 않을 경우 401을 반환한다.")
            @Test
            void ifInvalidToken() {
                given(jwtUtils.isValid(any())).willReturn(false);

                //when
                Mono<Void> mono = filter.filter(exchange, chain);

                //then
                assertThat(mockResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
            }

            @DisplayName("토큰이 유효할 경우 X-Authorization-Id 헤더에 userId 를 포함하고 패스한다.")
            @Test
            void ifValidToken() {
                String userId = "user01";

                given(jwtUtils.isValid(any())).willReturn(true);
                TokenUser mockUser = mock(TokenUser.class);
                given(mockUser.getUserId()).willReturn(userId);
                given(jwtUtils.decode(any())).willReturn(mockUser);
                ServerHttpRequest.Builder builder = mock(ServerHttpRequest.Builder.class);
                given(mockRequest.mutate()).willReturn(builder);
                given(builder.header(any(), any())).willReturn(builder);

                //when
                Mono<Void> mono = filter.filter(exchange, chain);

                //then
                then(builder).should().header("X-Authorization-Id", userId);
                then(chain).should(times(1)).filter(exchange);
            }


        }




    }



}
