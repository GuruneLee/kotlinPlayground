package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class PostStatus (
    @MapsId
    @ManyToOne
    val post: Post,

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    val status: PostStatusEnum,
) {
    @Id
    @Column(name = "ID")
    val id: Long = 0L
}

enum class PostStatusEnum {
    DRAFT,
    PUBLISHED,
    ARCHIVED,
    ;

    fun getCandidateStatus(): Set<PostStatusEnum> {
        return when (this) {
            DRAFT -> setOf(PUBLISHED, ARCHIVED)
            PUBLISHED -> setOf(ARCHIVED)
            ARCHIVED -> emptySet()
        }
    }
}

interface PostStatusRepository: CrudRepository<PostStatus, Long> {
}