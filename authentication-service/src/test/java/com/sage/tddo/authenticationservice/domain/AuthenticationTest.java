package com.sage.tddo.authenticationservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    /**
     * if id of password are null
     */
    @Test
    void ifIdOfPasswordAreNull() {
        assertThrows(IllegalArgumentException.class, () -> new Authentication("id", null));
        assertThrows(IllegalArgumentException.class, () -> new Authentication(null, "password"));
    }

}
