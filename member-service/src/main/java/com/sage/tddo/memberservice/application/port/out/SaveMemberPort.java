package com.sage.tddo.memberservice.application.port.out;

import com.sage.tddo.memberservice.domain.Member;

public interface SaveMemberPort {
    void save(Member member);
}
