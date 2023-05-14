package com.sage.tddo.authenticationservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    /**
     * if id is null or name is null
     * throw IllegalArgumentException
     */
    @Test
    void ifIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new Member("id", null));
        assertThrows(IllegalArgumentException.class, () -> new Member(null, "name"));
    }


}
