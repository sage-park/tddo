package com.sage.tddo.authenticationservice.adapter.out.persistence;

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
    private AuthorityRepository authorityRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        userDetailsService = new JpaUserDetailsService(
                memberRepository, authorityRepository
        );
    }

    //test loadUserByUsername method
    @Test
    void testLoadUserByUsername() {
        //given
        memberRepository.save(new Member("user01", "password01", true));
        authorityRepository.save(new Authority("user01", "ROLE_USER"));
        authorityRepository.save(new Authority("user01", "ADMIN"));


        UserDetails userDetails = userDetailsService.loadUserByUsername("user01");

        assertEquals(userDetails.getUsername(), "user01");
        assertEquals(userDetails.getPassword(), "password01");
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertEquals(userDetails.getAuthorities().size(), 2);
    }


}
