package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.application.port.out.SaveAuthenticationPort;
import com.sage.tddo.authenticationservice.domain.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaveAuthenticationJpaPort implements SaveAuthenticationPort {

    private final AuthenticationJpaRepository authenticationJpaRepository;

    @Override
    public void save(Authentication authentication) {
        AuthenticationJpaEntity entity = new AuthenticationJpaEntity(
                authentication.getId(),
                authentication.getPassword(),
                authentication.isEnabled()
        );
        authenticationJpaRepository.save(entity);
    }
}
