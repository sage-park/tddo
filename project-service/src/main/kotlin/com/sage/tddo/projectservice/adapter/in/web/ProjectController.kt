package com.sage.tddo.projectservice.adapter.`in`.web

import com.sage.tddo.projectservice.application.port.`in`.CreateProjectUsecase
import com.sage.tddo.projectservice.exception.CommonException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ProjectController (private val createProjectUsecase: CreateProjectUsecase) {


    @PostMapping("/projects")
    fun createProject(@RequestBody request: PostProjectRequest):ResponseEntity<PostProjectResponse> {

        val projectId: String
        try{
            projectId = createProjectUsecase.createProject(request.name)
        } catch (exception:Exception){
            throw CommonException(exception)
        }


        return ResponseEntity(PostProjectResponse(projectId), HttpStatus.CREATED)
    }

    @ExceptionHandler(CommonException::class)
    fun runtimeExceptionHandler():ResponseEntity<String> {
        return ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
