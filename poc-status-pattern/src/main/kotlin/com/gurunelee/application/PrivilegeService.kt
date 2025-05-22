package com.gurunelee.application

import org.springframework.stereotype.Service

@Service
class PrivilegeService {
    fun getPrivileges(): Set<String> {
        return setOf("EDIT", "COMMENT.ALL", "ARCHIVE")
    }
}