package com.sage.tddo.gatewayserver.filter;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "tddo-correlation-id";

    public String generateCorrelationId() {
        return String.valueOf(UUID.randomUUID());
    }
}
