package com.sage.tddo.authenticationservice.adapter.out.event;

import com.sage.tddo.authenticationservice.application.port.out.RegisterEventPort;
import com.sage.tddo.authenticationservice.config.EventPublisher;
import com.sage.tddo.authenticationservice.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterEventPublisherTest {

    RegisterEventPort registerEventPort;
    @Mock
    private EventPublisher eventPublisher;

    @BeforeEach

    void setUp() {
        registerEventPort = new RegisterEventPublisher(eventPublisher);
    }

    /**
     * member service 에 user 를 추가하는 요청을 날린다.
     */
    @Test
    void testToMemberService() {
        //given
        Member member = new Member("user01", "김구");
        //when
        registerEventPort.send(member);
        //then
        then(eventPublisher).should().send(eq("registerMember"), refEq(member));

    }

}
