package com.sage.tddo.authenticationservice.config;

import com.sage.tddo.authenticationservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final StreamBridge streamBridge;

    public void send(String bindingName, Object data) {
        streamBridge.send(bindingName, data);
    }

}
