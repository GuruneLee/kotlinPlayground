package com.gurunelee.common.dto

import com.gurunelee.common.domain.Member

/**
 * Created by chlee on 5/22/25.
 *
 * @author chlee
 * @since 5/22/25
 */
data class MemberResponse (
    val members: List<Member>
) {
    companion object {
        fun one(name: String, age: Long): MemberResponse {
            return MemberResponse(listOf(Member(null, name, age)))
        }
    }
}