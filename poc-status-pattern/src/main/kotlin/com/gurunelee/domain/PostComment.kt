package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class PostComment(
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post,

    @Column(name = "COMMENTS")
    val comment: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "COMMENT_TYPE", nullable = false)
    val commentType: CommentType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long = 0L
}

enum class CommentType {
    REVIEW,
    REPLY,
    ;
}

interface PostCommentRepository: CrudRepository<PostComment, Long>
