package com.gurunelee.pocswaggerui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRetrieveController {
    @GetMapping("/api/users/members")
    fun retrieveMembers(): List<Member> {
        return listOf(
            Member(age = 1L, name = "John Doe"),
            Member(age = 2L, name = "Jane Doe")
        )
    }

    @PostMapping("/api/users/members")
    fun createMember(
        name: String,
        age: Long,
    ): MemberResponse {
        return MemberResponse.one(name, age)
    }

    @PutMapping("/api/users/members/{id}")
    @Operation(summary = "Update Member")
    fun updateMember(
        @Parameter(description = "Member ID")
        @PathVariable id: String,
        name: String,
        age: Long,
    ): MemberResponse {
        val selected = Member(id, name, age).apply {
            this.name = "modified"
        }

        return MemberResponse(
            listOf(selected)
        )
    }
}