package com.sage.tddo.projectservice.application.port.out

import com.sage.tddo.projectservice.domain.Project

interface ProjectRepository {
    fun existsByName(name: String): Boolean
    fun save(project: Project)

}
