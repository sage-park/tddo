package com.sage.tddo.authenticationservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String id;
    private String password;
    private String name;
    private Boolean enabled;
}
