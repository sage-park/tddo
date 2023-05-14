package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthorityJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthorityJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class JpaUserDetailsServiceTest {

    UserDetailsService userDetailsService;

    @Autowired
    private AuthorityJpaRepository authorityJpaRepository;
    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @BeforeEach
    void setUp() {
        userDetailsService = new JpaUserDetailsService(
                authorityJpaRepository, authenticationJpaRepository
        );
    }

    //test loadUserByUsername method
    @Test
    void testLoadUserByUsername() {
        //given
        authenticationJpaRepository.save(new AuthenticationJpaEntity("user01", "password01", true));
        authorityJpaRepository.save(new AuthorityJpaEntity("user01", "ROLE_USER"));
        authorityJpaRepository.save(new AuthorityJpaEntity("user01", "ADMIN"));


        UserDetails userDetails = userDetailsService.loadUserByUsername("user01");

        assertEquals(userDetails.getUsername(), "user01");
        assertEquals(userDetails.getPassword(), "password01");
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertEquals(userDetails.getAuthorities().size(), 2);
    }


}
