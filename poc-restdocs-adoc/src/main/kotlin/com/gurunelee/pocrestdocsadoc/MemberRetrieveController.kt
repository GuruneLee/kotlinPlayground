package com.gurunelee.pocrestdocsadoc

import com.gurunelee.common.dto.MemberResponse
import com.gurunelee.pocrestdocsadoc.application.MemberRetrieveService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * Created by chlee on 5/22/25.
 *
 * @author chlee
 * @since 5/22/25
 */
@RestController
class MemberRetrieveController(
    private val memberRetrieveService: MemberRetrieveService,
) {
    @GetMapping("/members/{id}")
    fun retrieveMembers(
        @PathVariable id: String,
    ): MemberResponse {
        return memberRetrieveService.retrieveMembers(id)
    }
}