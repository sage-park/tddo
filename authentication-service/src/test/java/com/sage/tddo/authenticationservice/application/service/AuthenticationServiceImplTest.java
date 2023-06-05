package com.sage.tddo.authenticationservice.application.service;

import com.sage.tddo.authenticationservice.application.port.in.AuthenticationService;
import com.sage.tddo.authenticationservice.application.port.in.RegisterParam;
import com.sage.tddo.authenticationservice.application.port.out.LoadAuthenticationPort;
import com.sage.tddo.authenticationservice.application.port.out.SaveAuthenticationPort;
import com.sage.tddo.authenticationservice.application.port.out.RegisterEventPort;
import com.sage.tddo.authenticationservice.domain.Authentication;
import com.sage.tddo.authenticationservice.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * test class for UserRegistering
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    AuthenticationService authenticationService;
    @Mock
    private SaveAuthenticationPort saveAuthenticationPort;
    @Mock
    private RegisterEventPort registerEventPort;

    @Mock
    private LoadAuthenticationPort loadAuthenticationPort;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(saveAuthenticationPort, registerEventPort, loadAuthenticationPort);
    }

    /**
     * test for registerUser
     * if id already exist, return error
     */
    @Test
    void ifIdAlreadyExist() {
        //given
        String id = "user01";
        RegisterParam param = new RegisterParam(id, "password01", "김구", true);

        given(loadAuthenticationPort.isExist(id)).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> authenticationService.register(param));

        //then
        assertThat(throwable).isInstanceOf(UserIdAlreadyExistException.class);
    }

    /**
     * if id is not exist, save Authentication
     */
    @Test
    void ifNotExist() {
        //given
        RegisterParam param = new RegisterParam("user01", "password01", "김구", true);

        //when
        authenticationService.register(param);

        //then
        ArgumentCaptor<Authentication> argumentCaptor = ArgumentCaptor.forClass(Authentication.class);
        then(saveAuthenticationPort).should().save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getId()).isEqualTo(param.getId());
        assertThat(argumentCaptor.getValue().getPassword()).isEqualTo(param.getPassword());
        assertThat(argumentCaptor.getValue().isEnabled()).isEqualTo(param.isEnabled());

    }

    /**
     * if id is not exist, save user info
     */
    @Test
    void saveUserInfo() {
        //given
        RegisterParam param = new RegisterParam("user01", "password01", "김구", true);

        //when
        authenticationService.register(param);

        //then
        ArgumentCaptor<Member> argumentCaptor = ArgumentCaptor.forClass(Member.class);
        then(registerEventPort).should().send(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getId()).isEqualTo(param.getId());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(param.getName());

    }


}
