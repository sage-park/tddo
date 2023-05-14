package com.sage.tddo.authenticationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    @Value("${tddo.gatewayserver.baseurl}")
    private String gatewayUrl;

    @Bean
    public RestTemplate customRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri(gatewayUrl).build();
    }


}
