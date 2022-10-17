package com.ep.example

import com.ep.example.sample.SampleApi
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SampleApi::class])
@AutoConfigureRestDocs
class SampleApiTest : RestControllerTest() {

    @Test
    fun `get 테스트`() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/sample")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "get",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            ).andDo(
                MockMvcRestDocumentationWrapper.document(
                    "get",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            )
    }

    @Test
    fun `post 테스트`() {
        val request = Request(name = "sample", price = 3000)

        mockMvc.post("/api/sample") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(
                document(
                    "post",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            )
        }
    }

    @Test
    fun `put 테스트`() {
        val request = Request(name = "sample", price = 3000)

        mockMvc.put("/api/sample") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(
                document(
                    "put",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            )
        }
    }

    @Test
    fun `patch 테스트`() {
        val request = Request(name = "sample", price = 3000)

        mockMvc.patch("/api/sample") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(
                document(
                    "patch",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            )
        }
    }

    @Test
    fun `delete 테스트`() {
        mockMvc.delete("/api/sample") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }.andDo {
            handle(
                document(
                    "delete",
                    getDocumentRequest(),
                    getDocumentResponse()
                )
            )
        }
    }

    data class Request(
        val name: String,
        val price: Int
    )
}
