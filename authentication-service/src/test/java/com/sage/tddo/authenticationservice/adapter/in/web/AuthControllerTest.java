package com.sage.tddo.authenticationservice.adapter.in.web;

import com.sage.tddo.authenticationservice.adapter.in.web.AuthController;
import com.sage.tddo.authenticationservice.config.security.SecurityConfig;
import com.sage.tddo.authenticationservice.config.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AuthController test
 */
@WebMvcTest(controllers = AuthController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    /**
     * if the existing username or password are not given
     * when post to /auth/authenticate
     * then return 400
     */
    @Test
    void testAuthControllerWithBadCredentials() throws Exception {

        //when
        ResultActions action = mockMvc.perform(
                post("/auth/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"\",\"password\":\"test\"}")
        );

        //then
        action
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid username or password"))
                .andExpect(jsonPath("$.code").value("ERROR_002"))
        ;


        //when
        ResultActions action02 = mockMvc.perform(
                post("/auth/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"test\",\"password\":\"\"}")
        );

        //then
        action02
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid username or password"))
                .andExpect(jsonPath("$.code").value("ERROR_002"))
        ;

    }

    /**
     * if the existing username and correct password are given
     * when post to /auth/authenticate
     * then return 200 and the token
     * and the token is stored in the cookie
     */
    @Test
    void testAuthController() throws Exception {

        //given
        Authentication authentication = new TestingAuthenticationToken(
                "test",
                "test",
                "ROLE_USER"
        );
        given(authenticationManager.authenticate(any()))
                .willReturn(authentication);
        given(jwtUtils.generateToken(authentication.getName(), authentication.getAuthorities()))
                .willReturn("token01");

        //when
        ResultActions action = mockMvc.perform(
                post("/auth/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"test\",\"password\":\"test\"}")
        );

        //then
        action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token01"))
        ;

        //cookie에 token이 들어가는지 확인
        MockHttpServletResponse response = action.andReturn().getResponse();
        String token = response.getCookie("jwt").getValue();
        assertNotNull(token);


    }

    /**
     * if the existing username and wrong password are given
     * when post to /auth/authenticate
     * then return 401
     */
    @Test
    void testAuthController2() throws Exception {

        //given
        given(authenticationManager.authenticate(any()))
                .willThrow(new BadCredentialsException("test"));

        //when
        ResultActions action = mockMvc.perform(
                post("/auth/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"test\",\"password\":\"test\"}")
        );

        //then
        action
                .andExpect(status().isUnauthorized())
        ;

    }



}
