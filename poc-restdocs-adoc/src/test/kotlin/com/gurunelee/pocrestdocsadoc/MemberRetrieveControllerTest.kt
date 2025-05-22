package com.gurunelee.pocrestdocsadoc

import com.gurunelee.pocrestdocsadoc.application.MemberRetrieveService
import com.gurunelee.pocrestdocsadoc.common.domain.Member
import com.gurunelee.pocrestdocsadoc.common.dto.MemberResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.assertj.MockMvcTester
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * Created by chlee on 5/22/25.
 *
 * @author chlee
 * @since 5/22/25
 */
@WebMvcTest(MemberRetrieveController::class)
@AutoConfigureRestDocs
class MemberRetrieveControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvcTester

    @MockkBean // @MockBean 은 Spring Boot 3.4.0 부터 Deprecated 됨
    private lateinit var memberRetrieveService: MemberRetrieveService

    @Test
    fun retrieveMembers() {
        // given
        val id = "1"
        every { memberRetrieveService.retrieveMembers(id) } returns MemberResponse.singleList(
            Member(
                id = id,
                name = "John Doe",
                age = 1L
            )
        )

        // when
        val perform = mockMvc.perform(
            get("/members/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        assertThat(perform)
            .apply(
                document(
                    "retrieve-members",
                    pathParameters(
                        parameterWithName("id").description("Member ID")
                    )
                )
            )
    }

}