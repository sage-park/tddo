package com.sage.tddo.gatewayserver.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "filter.auth")
@Data
public class FilterProperties {
    private List<String > excludedUrls;
}
