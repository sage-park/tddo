package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.application.port.out.SaveAuthenticationPort;
import com.sage.tddo.authenticationservice.domain.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class SaveAuthenticationJpaPortTest {

    SaveAuthenticationPort saveAuthenticationPort;

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @BeforeEach
    void setUp() {
        saveAuthenticationPort = new SaveAuthenticationJpaPort(authenticationJpaRepository);
    }

    /**
     * test save authentication
     */
    @Test
    void testSaveAuthentication() {

        //given
        Authentication authentication = new Authentication("user01", "password01", true);

        //when
        saveAuthenticationPort.save(authentication);

        //then
        AuthenticationJpaEntity authenticationJpaEntity = authenticationJpaRepository.findById(authentication.getId()).get();
        assertThat(authenticationJpaEntity.getId()).isEqualTo(authentication.getId());
        assertThat(authenticationJpaEntity.getPassword()).isEqualTo(authentication.getPassword());
        assertThat(authenticationJpaEntity.isEnabled()).isEqualTo(authentication.isEnabled());


        //given
        Authentication authentication02 = new Authentication("user02", "password02", false);

        //when
        saveAuthenticationPort.save(authentication02);

        //then
        AuthenticationJpaEntity authenticationJpaEntity02 = authenticationJpaRepository.findById(authentication02.getId()).get();
        assertThat(authenticationJpaEntity02.getId()).isEqualTo(authentication02.getId());
        assertThat(authenticationJpaEntity02.getPassword()).isEqualTo(authentication02.getPassword());
        assertThat(authenticationJpaEntity02.isEnabled()).isEqualTo(authentication02.isEnabled());


    }

}
