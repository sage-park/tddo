package com.sage.tddo.authenticationservice.application.port.out;

import com.sage.tddo.authenticationservice.domain.Authentication;

public interface SaveAuthenticationPort {
    void save(Authentication authentication);
}
