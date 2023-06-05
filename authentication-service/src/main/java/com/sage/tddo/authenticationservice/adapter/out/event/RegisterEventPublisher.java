package com.sage.tddo.authenticationservice.adapter.out.event;

import com.sage.tddo.authenticationservice.application.port.out.RegisterEventPort;
import com.sage.tddo.authenticationservice.config.EventPublisher;
import com.sage.tddo.authenticationservice.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RegisterEventPublisher implements RegisterEventPort {

    private final EventPublisher eventPublisher;

    @Override
    public void send(Member member) {
        eventPublisher.send("registerMember", member);
        log.info("registerMember 이벤트 발신 : " + member);
    }

}
