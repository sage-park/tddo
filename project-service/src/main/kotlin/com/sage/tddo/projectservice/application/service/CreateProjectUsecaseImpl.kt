package com.sage.tddo.projectservice.application.service

import com.sage.tddo.projectservice.application.port.`in`.CreateProjectUsecase
import com.sage.tddo.projectservice.application.port.out.ProjectRepository
import com.sage.tddo.projectservice.domain.Project
import com.sage.tddo.projectservice.exception.ProjectAlreadyExistsException
import org.springframework.stereotype.Service

@Service
class CreateProjectUsecaseImpl(
    private val projectIdGenerator:ProjectIdGenerator,
    private val projectRepository: ProjectRepository
) : CreateProjectUsecase {
    override fun createProject(name: String): String {

        if (projectRepository.existsByName(name)) {
            throw ProjectAlreadyExistsException()
        }

        val project = Project(projectIdGenerator.generateId(), name)
        projectRepository.save(project)

        return project.id
    }
}
