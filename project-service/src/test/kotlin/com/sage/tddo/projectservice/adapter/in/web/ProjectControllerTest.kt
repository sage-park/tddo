package com.sage.tddo.projectservice.adapter.`in`.web

import com.sage.tddo.projectservice.application.port.`in`.CreateProjectUsecase
import com.sage.tddo.projectservice.config.SecurityConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(controllers = [ProjectController::class])
@ExtendWith(MockitoExtension::class)
@ActiveProfiles("test")
@Import(SecurityConfig::class)
class ProjectControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var createProjectUsecase: CreateProjectUsecase

    @DisplayName("프로젝트를 등록하는 API 테스트")
    @Nested
    @WithMockUser
    inner class CreateProject {

        @DisplayName("""
            requestBody에 name이 잘 들어가있고
            createProjectUsecase(name)이 성공적으로 실행되면
            201 과 project id 를 반환한다.
        """)
        @Test
        fun createProjectTest() {
            testIfSuccessCreated("TDDO", "TDDO_KEY")
            testIfSuccessCreated("TDDO01", "TDDO01_KEY")
        }

        fun testIfSuccessCreated(name:String, projectId:String){
            //given
            whenever(createProjectUsecase.createProject(name))
                .thenReturn(projectId)

            //when
            val resultActions = callCreateProject(name)

            //then
            then(createProjectUsecase)
                .should()
                .createProject(eq(name))

            resultActions
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(projectId))

        }

        private fun callCreateProject(name: String): ResultActions {
            val result = mockMvc.perform(
                post("/api/v1/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"name\": \"${name}\"\n}")
            )

            result.andDo(log())

            return result

        }

        @DisplayName("requestBody 에 name이 없을 경우 400을 반환한다.")
        @Test
        fun ifIllegalArg() {

            val name = ""

            val resultActions = callCreateProject(name)
            resultActions.andExpect(status().is4xxClientError)
        }

        @DisplayName("project 를 생성하는 도중에 오류발생시 500을 반환한다.")
        @Test
        fun ifErrorWhenExecuteCreateProject() {

            //given
            val name = "TDDO"
            whenever(createProjectUsecase.createProject(any())).thenThrow(RuntimeException())

            //when
            val resultActions = callCreateProject(name)

            //then
            resultActions.andExpect(status().is5xxServerError)

        }





    }



}

