package com.sage.tddo.authenticationservice.adapter.out.persistence;

import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthenticationJpaRepository;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthorityJpaEntity;
import com.sage.tddo.authenticationservice.adapter.out.persistence.jpa.AuthorityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final AuthorityJpaRepository authorityJpaRepository;
    private final AuthenticationJpaRepository authenticationJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthenticationJpaEntity authentication = authenticationJpaRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<AuthorityJpaEntity> authorities = authorityJpaRepository.findAllByUsername(username);

        return new JpaUserDetails(authentication, authorities);
    }
}
