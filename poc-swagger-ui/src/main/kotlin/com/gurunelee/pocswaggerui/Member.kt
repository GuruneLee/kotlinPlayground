package com.gurunelee.pocswaggerui

data class Member (
    val id: String? = null,
    var name: String,
    val age: Long,
)

data class MemberResponse (
    val members: List<Member>
) {
    companion object {
        fun one(name: String, age: Long): MemberResponse {
            return MemberResponse(listOf(Member(null, name, age)))
        }
    }
}