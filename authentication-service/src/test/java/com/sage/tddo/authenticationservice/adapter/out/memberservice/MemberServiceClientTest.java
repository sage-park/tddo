package com.sage.tddo.authenticationservice.adapter.out.memberservice;

import com.sage.tddo.authenticationservice.application.port.out.SaveUserInfoPort;
import com.sage.tddo.authenticationservice.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceClientTest {

    SaveUserInfoPort saveUserInfoPort;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        saveUserInfoPort = new MemberServiceClient(restTemplate);
    }

    /**
     * member service 에 user 를 추가하는 요청을 날린다.
     */
    @Test
    void testToMemberService() {
        //given
        Member member = new Member("user01", "김구");
        //when
        saveUserInfoPort.save(member);
        //then
        then(restTemplate).should().postForEntity(eq("/member-service/member"), refEq(member), eq(String.class));

    }

}
