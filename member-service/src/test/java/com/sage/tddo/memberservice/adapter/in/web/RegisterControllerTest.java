package com.sage.tddo.memberservice.adapter.in.web;

import com.sage.tddo.memberservice.application.port.in.UserRegisterUsecase;
import com.sage.tddo.memberservice.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserRegisterUsecase userRegisterUsecase;

    /**
     * if success return 200
     */
    @Test
    void postMember() throws Exception {

        //given

        //when
        ResultActions result = mockMvc.perform(
                post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\":" +
                                "  \"user01\",\n" +
                                "  \"name\": \"김구\"\n" +
                                "}")
        );

        //then
        result.andExpect(status().isCreated());
        then(userRegisterUsecase).should().register("user01", "김구");

    }

    /**
     * if fail return 500
     */
    @Test
    void postMemberFail() throws Exception {

        //given
        willThrow(RuntimeException.class)
                .given(userRegisterUsecase).register(any(), any());
        //when
        ResultActions result = mockMvc.perform(
                post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\":" +
                                "  \"user01\",\n" +
                                "  \"name\": \"김구\"\n" +
                                "}")
        );

        //then
        result.andExpect(status().isInternalServerError());


    }





}
