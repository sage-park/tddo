package com.sage.tddo.memberservice.adapter.in.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMemberRequest {
    private String id;
    private String name;
}
