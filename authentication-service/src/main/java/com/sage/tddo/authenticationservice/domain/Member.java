package com.sage.tddo.authenticationservice.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

public class Member {
    @Getter
    private String id;
    @Getter
    private String name;

    public Member(String id, String name) {

        if (!StringUtils.hasLength(id) || !StringUtils.hasLength(name)) {
            throw new IllegalArgumentException("id or name is empty");
        }

        this.id = id;
        this.name = name;
    }
}
