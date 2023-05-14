package com.sage.tddo.authenticationservice.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

public class Authentication {
    @Getter
    private String id;

    @Getter
    private String password;

    @Getter
    private boolean enabled;

    public Authentication(String id, String password, boolean enabled) {
        if (!StringUtils.hasLength(id) || !StringUtils.hasLength(password)) {
            throw new IllegalArgumentException("id or password is empty");
        }

        this.id = id;
        this.password = password;
        this.enabled = enabled;
    }
}
