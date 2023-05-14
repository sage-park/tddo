package com.sage.tddo.authenticationservice.adapter.out.memberservice;

import com.sage.tddo.authenticationservice.application.port.out.SaveUserInfoPort;
import com.sage.tddo.authenticationservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class MemberServiceClient implements SaveUserInfoPort {

    private final RestTemplate customRestTemplate;

    @Override
    public void save(Member member) {
        customRestTemplate.postForEntity("/member-service/member", member, String.class);
    }
}
