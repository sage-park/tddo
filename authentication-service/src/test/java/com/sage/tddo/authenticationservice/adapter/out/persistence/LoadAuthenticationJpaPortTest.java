package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.netflix.discovery.converters.Auto;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.application.service.AuthenticationNotFoundException;
import com.sage.tddo.authenticationservice.domain.Authentication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class LoadAuthenticationJpaPortTest {

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    private LoadAuthenticationJpaPort loadAuthenticationJpaPort;

    @BeforeEach
    void setUp() {
        loadAuthenticationJpaPort = new LoadAuthenticationJpaPort(authenticationJpaRepository);
    }

    /**
     * test exist authentication
     */
    @Test
    void existAuthentication() {
        //given
        authenticationJpaRepository.save(new AuthenticationJpaEntity("id", "password"));

        //when
        boolean exist = loadAuthenticationJpaPort.isExist("id");

        //then
        assertThat(exist).isTrue();
    }

    /**
     * test load authentication
     */
    @Test
    void loadAuthentication() {
        //given
        authenticationJpaRepository.save(new AuthenticationJpaEntity("id", "password"));

        //when
        Authentication authentication = loadAuthenticationJpaPort.load("id");

        //then
        assertThat(authentication.getId()).isEqualTo("id");
        assertThat(authentication.getPassword()).isEqualTo("password");

    }

    /**
     * if not exist
     * throw AuthenticationNotFoundException
     */
    @Test
    void notExistAuthentication() {

        //when
        Exception exception = catchException(() -> loadAuthenticationJpaPort.load("id"));

        //then
        assertThat(exception).isInstanceOf(AuthenticationNotFoundException.class);

    }

}
