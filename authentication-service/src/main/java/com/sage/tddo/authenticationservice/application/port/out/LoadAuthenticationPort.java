package com.sage.tddo.authenticationservice.application.port.out;

import com.sage.tddo.authenticationservice.domain.Authentication;

public interface LoadAuthenticationPort {
    boolean isExist(String id);

    Authentication load(String id);
}
