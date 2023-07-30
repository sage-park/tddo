package com.sage.tddo.gatewayserver.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonWebTokenUtils  {

    private final JwtProperties jwtProperties;

    public boolean isValid(String token) {

        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public TokenUser decode(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();

        String id = claims.getSubject();

        return new TokenUser(id);
    }

}
