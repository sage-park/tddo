package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.application.port.out.LoadAuthenticationPort;
import com.sage.tddo.authenticationservice.application.port.out.SaveAuthenticationPort;
import com.sage.tddo.authenticationservice.application.service.AuthenticationNotFoundException;
import com.sage.tddo.authenticationservice.domain.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoadAuthenticationJpaPort implements LoadAuthenticationPort {

    private final AuthenticationJpaRepository authenticationJpaRepository;

    @Override
    public boolean isExist(String id) {
        return authenticationJpaRepository.existsById(id);
    }

    @Override
    public Authentication load(String id) {

        AuthenticationJpaEntity authenticationJpaEntity = authenticationJpaRepository.findById(id).orElseThrow(AuthenticationNotFoundException::new);

        return new Authentication(
                authenticationJpaEntity.getId(),
                authenticationJpaEntity.getPassword(),
                authenticationJpaEntity.isEnabled()
        );
    }
}
