package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "POST_STATUS")
class PostStatusEntity(
    @MapsId
    @ManyToOne
    val post: Post,

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    var status: PostStatusEnum,
) {
    @Id
    val id: Long = 0L

    fun updateStatus(status: PostStatusEnum) {
        if (status !in this.status.getCandidateStatus()) {
            return
        }
        this.status = status
    }
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

interface PostStatusRepository : CrudRepository<PostStatusEntity, Long>