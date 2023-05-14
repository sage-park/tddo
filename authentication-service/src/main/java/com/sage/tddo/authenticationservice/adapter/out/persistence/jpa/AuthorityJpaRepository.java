package com.sage.tddo.authenticationservice.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<AuthorityJpaEntity, Long> {

    List<AuthorityJpaEntity> findAllByUsername(String username);
}
