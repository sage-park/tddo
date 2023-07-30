package com.sage.tddo.projectservice.adapter.out.persistence

import com.sage.tddo.projectservice.adapter.out.persistence.jpa.ProjectJpaEntity
import com.sage.tddo.projectservice.adapter.out.persistence.jpa.ProjectJpaRepository
import com.sage.tddo.projectservice.application.port.out.ProjectRepository
import com.sage.tddo.projectservice.domain.Project
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.BeforeTest
import kotlin.test.Test

@DataJpaTest
@ActiveProfiles("test")
class JpaProjectRepositoryTest {

    @Autowired
    lateinit var projectJpaRepository: ProjectJpaRepository

    private lateinit var projectRepository: ProjectRepository

    @BeforeTest
    fun setUp() {
        projectRepository = JpaProjectRepository(projectJpaRepository)
    }

    @Test
    fun test() {

        //when
        projectRepository.save(Project("test", "test"))

        //then
        val exist = projectRepository.existsByName("test")
        assertThat(exist).isTrue()

    }



}
