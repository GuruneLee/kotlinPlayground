package com.gurunelee.pocrestdocsadoc.application

import com.gurunelee.pocrestdocsadoc.common.domain.Member
import com.gurunelee.pocrestdocsadoc.common.dto.MemberResponse
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

/**
 * Created by chlee on 5/22/25.
 *
 * @author chlee
 * @since 5/22/25
 */
@Service
class MemberRetrieveService(
    private val memberRepository: MemberRepository,
) {
    fun retrieveMembers(id: String): MemberResponse {
        return MemberResponse.singleList(memberRepository.findById(id))
    }
}

@Repository
class MemberRepository {
    fun findById(id: String): Member {
        return Member(id, "John Doe", 1L)
    }
}