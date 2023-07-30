package com.sage.tddo.authenticationservice.adapter.out.client;

import com.sage.tddo.authenticationservice.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceClientTest {

    MemberServiceClient memberServiceClient;
    @Mock
    private RestTemplate customRestTemplate;

    @BeforeEach
    void setUp() {
        memberServiceClient = new MemberServiceClient(customRestTemplate);
    }

    @Test
    void send() {
        //given
        Member member = new Member("user01", "김구");
        //when
        memberServiceClient.send(member);
        //then
        then(customRestTemplate).should().postForEntity("/member-service/members", member, String.class);
    }


}
