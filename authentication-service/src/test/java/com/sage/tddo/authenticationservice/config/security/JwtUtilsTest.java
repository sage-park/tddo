package com.sage.tddo.authenticationservice.config.security;

import com.sage.tddo.authenticationservice.config.security.JwtUtils;
import com.sage.tddo.authenticationservice.config.security.ServiceConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    JwtUtils jwtUtils;

    @Mock
    private ServiceConfig serviceConfig;

    private static String TEST_SECRET_KEY = "test_secret_key";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(serviceConfig);

        given(serviceConfig.getJwtSigningKey()).willReturn(TEST_SECRET_KEY);
    }

    /**
     * Token generation test
     */
    @Test
    void generateTokenTest() {

        String username = "username";
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                (GrantedAuthority) () -> "ROLE_USER"
        );
        String token = jwtUtils.generateToken(username, authorities);

        assertTrue(jwtUtils.validateToken(token, username));

    }

    /**
     * if token's username is different, return false
     */
    @Test
    void validateTokenTest() {
        String token = jwtUtils.generateToken("user01", null);

        boolean validate = jwtUtils.validateToken(token, "username");

        assertFalse(validate);
    }

    /**
     * if token is expired return false
     */
    @Test
    void validateTokenTest02() {

        // create an expired token (for testing purposes only)
        String expiredToken = Jwts.builder()
                .setSubject("username")
                .setExpiration(new Date()) // expired 10 seconds ago
                .signWith(SignatureAlgorithm.HS256, serviceConfig.getJwtSigningKey())
                .compact();

        assertThrows(ExpiredJwtException.class, () -> {
            jwtUtils.validateToken(expiredToken, "username");
        });

    }

}
