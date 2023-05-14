package com.sage.tddo.authenticationservice.application.port.in;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterParamTest {

    /**
     * if userId is null, throw IllegalArgumentException
     */
    @Test
    void testUserIdIsNull() {
        //given
        String userId = null;
        String password = "password01";
        String userName = "김구";

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RegisterParam(userId, password, userName);
        });

        //then
        assertEquals("userId is null", exception.getMessage());
    }

    /**
     * if password is null, throw IllegalArgumentException
     */
    @Test
    void testPasswordIsNull() {
        //given
        String userId = "user01";
        String password = null;
        String userName = "김구";

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RegisterParam(userId, password, userName);
        });

        //then
        assertEquals("password is null", exception.getMessage());
    }

    /**
     * if userName is null, throw IllegalArgumentException
     */
    @Test
    void testUserNameIsNull() {
        //given
        String userId = "user01";
        String password = "password01";
        String userName = null;

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RegisterParam(userId, password, userName);
        });

        //then
        assertEquals("userName is null", exception.getMessage());
    }

}
