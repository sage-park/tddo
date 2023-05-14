package com.sage.tddo.authenticationservice.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationJpaRepository extends JpaRepository<AuthenticationJpaEntity, String> {
}
