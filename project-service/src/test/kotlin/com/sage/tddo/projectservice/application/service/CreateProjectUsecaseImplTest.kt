package com.sage.tddo.projectservice.application.service

import com.sage.tddo.projectservice.application.port.`in`.CreateProjectUsecase
import com.sage.tddo.projectservice.application.port.out.ProjectRepository
import com.sage.tddo.projectservice.domain.Project
import com.sage.tddo.projectservice.exception.ProjectAlreadyExistsException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.lang.RuntimeException
import kotlin.io.path.fileVisitor
import kotlin.test.*


@ExtendWith(MockitoExtension::class)
class CreateProjectUsecaseImplTest{

    private val projectRepository: ProjectRepository = mock<ProjectRepository>()
    var projectIdGenerator: ProjectIdGenerator = mock<ProjectIdGenerator>()

    var createProjectUsecase: CreateProjectUsecase = CreateProjectUsecaseImpl(projectIdGenerator, projectRepository)

    @Nested
    inner class CreateProject {

        @DisplayName("name이 존재하지 않다면")
        @Nested
        inner class ifNameNotExist {

            @BeforeTest
            fun before(){
                whenever(projectRepository.existsByName(any())).thenReturn(false)
            }

            @DisplayName("프로젝트를 생성하고 성공시 id 를 반환한다.")
            @Test
            fun whenSuccessCreateProject(){

                testCreateProject("TDDO", "TDDO_KEY")
                testCreateProject("TDDO1", "TDDO_KEY2")
                testCreateProject("TDDO2", "TDDO_KEY3")
            }
            private fun testCreateProject(name: String, generatedId: String) {
                //given
                whenever(projectIdGenerator.generateId()).thenReturn(generatedId)

                //when
                val id = createProjectUsecase.createProject(name)

                //then
                assertThat(id).isEqualTo(generatedId)
                then(projectRepository).should(times(1)).save(refEq(Project(generatedId, name)))
            }

        }


        @DisplayName("name이 이미 존재한다면 에러를 반환한다.")
        @Test
        fun ifNameExist(){
            //given
            val name = "이미 있는 이름"
            whenever(projectRepository.existsByName(name)).thenReturn(true)

            //when
            val exception = catchException { createProjectUsecase.createProject(name) }

            //then
            assertIs<ProjectAlreadyExistsException>(exception)
        }
    }



}
