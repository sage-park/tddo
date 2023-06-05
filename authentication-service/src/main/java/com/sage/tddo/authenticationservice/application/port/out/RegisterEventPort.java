package com.sage.tddo.authenticationservice.application.port.out;

import com.sage.tddo.authenticationservice.domain.Member;

public interface RegisterEventPort {
    void send(Member member);
}
