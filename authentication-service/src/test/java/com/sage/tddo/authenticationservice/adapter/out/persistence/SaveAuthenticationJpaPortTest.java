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
        Authentication authentication = new Authentication("user01", "password01");

        //when
        saveAuthenticationPort.save(authentication);

        //then
        AuthenticationJpaEntity authenticationJpaEntity = authenticationJpaRepository.findById(authentication.getId()).get();
        assertThat(authenticationJpaEntity.getId()).isEqualTo(authentication.getId());
        assertThat(authenticationJpaEntity.getPassword()).isEqualTo(authentication.getPassword());

    }

}
