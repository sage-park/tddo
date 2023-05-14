package com.sage.tddo.memberservice.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class Member {
    private String id;
    private String name;
    public Member(String id, String name) {

        if (!StringUtils.hasLength(id) || !StringUtils.hasLength(name))
            throw new IllegalArgumentException();

        this.id = id;
        this.name = name;
    }
}
