package com.sage.tddo.authenticationservice.adapter.in.web;

import com.sage.tddo.authenticationservice.application.port.in.AuthenticationService;
import com.sage.tddo.authenticationservice.application.port.in.RegisterParam;
import com.sage.tddo.authenticationservice.application.service.UserIdAlreadyExistException;
import com.sage.tddo.authenticationservice.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Register member Controller test
 */
@WebMvcTest(controllers = RegisterController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private PasswordEncoder passwordEncoder;


    /**
     * if given username is not exist
     * when post to /auth/register
     * then return 201
     */
    @Test
    void testRegister() throws Exception {

        //given
        given(passwordEncoder.encode(any())).willReturn("encodedPassword");

        //when
        ResultActions result = mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"test01\",\"password\":\"pass01\",\"name\": \"tester\"}")
        );

        //then
        result
                .andExpect(status().isCreated());

        ArgumentCaptor<RegisterParam> captor = ArgumentCaptor.forClass(RegisterParam.class);
        then(authenticationService).should().register(captor.capture());

        RegisterParam param = captor.getValue();
        assertEquals("test01", param.getId());
        assertEquals(passwordEncoder.encode("pass01"), param.getPassword());
        assertEquals("tester", param.getName());

    }

    /**
     * if the existing username are given
     * when post to /auth/register
     * then return 400
     * and return error message : Username is already taken.
     * and return error code : ERROR_001
     */
    @Test
    void testRegisterWithExistingUsername() throws Exception {

        //given
        given(passwordEncoder.encode(any())).willReturn("encodedPassword");
        willThrow(UserIdAlreadyExistException.class)
                .given(authenticationService).register(any());

        //when
        ResultActions result = mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"test\",\"password\":\"test\",\"name\": \"user\"}")
        );

        //then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username is already taken."))
                .andExpect(jsonPath("$.code").value("ERROR_001"));

    }

    /**
     * if the id or password or name are not given
     * when post to /auth/register
     * then return 400
     */
    @Test
    void testRegisterWithBadCredentials() throws Exception {

        mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"\",\"password\":\"test\",\"name\": \"user\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("id is required"));

        mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"user01\",\"password\":\"\",\"name\":\"user\"}")
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("password is required"));

        mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"user01\",\"password\":\"test\",\"name\":\"\"}")
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("name is required"));




    }

    /**
     * given
     * the id and password and name are given
     * and the username is not exist
     * and will throw exception when register
     *
     * when post to /auth/register
     *
     * then return 500
     */
    @Test
    void testRegisterWithException() throws Exception {

        //given
        willThrow(new RuntimeException("test")).given(authenticationService).register(any());

        //when
        ResultActions result = mockMvc.perform(
                post("/auth/register")
                        .contentType("application/json")
                        .content("{\"id\":\"test01\",\"password\":\"pass01\",\"name\": \"tester\"}")
        );

        //then
        result
                .andExpect(status().isInternalServerError());

    }






}
