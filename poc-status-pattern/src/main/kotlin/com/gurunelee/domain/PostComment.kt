package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class PostComment(
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post,

    @Column(name = "COMMENTS")
    val comment: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long = 0L
}

interface PostCommentRepository: CrudRepository<PostComment, Long>
