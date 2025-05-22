package com.gurunelee.pocrestdocsadoc.common.dto

import com.gurunelee.pocrestdocsadoc.common.domain.Member


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
        fun singleList(member: Member): MemberResponse {
            return MemberResponse(listOf(member))
        }
    }
}