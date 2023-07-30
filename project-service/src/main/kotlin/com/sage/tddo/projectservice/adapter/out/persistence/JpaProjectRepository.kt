package com.sage.tddo.projectservice.adapter.out.persistence

import com.sage.tddo.projectservice.adapter.out.persistence.jpa.ProjectJpaEntity
import com.sage.tddo.projectservice.adapter.out.persistence.jpa.ProjectJpaRepository
import com.sage.tddo.projectservice.application.port.out.ProjectRepository
import com.sage.tddo.projectservice.domain.Project
import org.springframework.stereotype.Repository

@Repository
class JpaProjectRepository(private val projectJpaRepository: ProjectJpaRepository) : ProjectRepository{


    override fun existsByName(name: String): Boolean {
        return projectJpaRepository.existsByName(name)
    }

    override fun save(project: Project) {
        projectJpaRepository.save(ProjectJpaEntity(project.id, project.name))
    }
}
