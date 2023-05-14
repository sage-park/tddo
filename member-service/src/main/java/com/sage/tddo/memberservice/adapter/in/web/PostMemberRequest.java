package com.sage.tddo.memberservice.adapter.in.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostMemberRequest {
    private String id;
    private String name;
}
