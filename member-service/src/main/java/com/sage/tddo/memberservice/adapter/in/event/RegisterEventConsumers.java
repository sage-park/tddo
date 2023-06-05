package com.sage.tddo.memberservice.adapter.in.event;

import com.sage.tddo.memberservice.application.port.in.UserRegisterUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RegisterEventConsumers {

    private final UserRegisterUsecase userRegisterUsecase;

    @Bean
    public Consumer<RegisterMemberRequest> registerMember() {
        return (request) -> {
            log.info("Registering 이벤트 수신: {}", request);
            userRegisterUsecase.register(request.getId(), request.getName());
        };

    }
}
