package com.sage.tddo.projectservice.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectJpaRepository : JpaRepository<ProjectJpaEntity, String> {

    fun existsByName(name: String): Boolean

}
