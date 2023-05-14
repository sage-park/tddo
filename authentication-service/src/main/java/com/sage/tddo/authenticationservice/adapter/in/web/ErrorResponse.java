package com.sage.tddo.authenticationservice.adapter.in.web;

import lombok.Data;

@Data
public class ErrorResponse {

    private ErrorCode code;
    private String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }
}
