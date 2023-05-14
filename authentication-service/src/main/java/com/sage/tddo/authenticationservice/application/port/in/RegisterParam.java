package com.sage.tddo.authenticationservice.application.port.in;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class RegisterParam {

    private final String id;
    private final String password;
    private final String name;

    public RegisterParam(String id, String password, String name) {
        if (!StringUtils.hasLength(id)) {
            throw new IllegalArgumentException("userId is null");
        }

        if (!StringUtils.hasLength(password)) {
            throw new IllegalArgumentException("password is null");
        }

        if (!StringUtils.hasLength(name)) {
            throw new IllegalArgumentException("userName is null");
        }

        this.id = id;
        this.password = password;
        this.name = name;
    }
}
