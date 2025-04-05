package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class Post(
    @Column(name = "TITLE")
    val title: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long = 0L

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val comments: MutableSet<PostComment> = mutableSetOf()

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private val _statuses: MutableSet<PostStatus> = mutableSetOf()
    val status: PostStatus
        get() = _statuses.firstOrNull()
            ?: throw IllegalStateException("PostStatus[${id}] not found")

    fun addStatus(status: PostStatusEnum) {
        _statuses.add(
            PostStatus(
                post = this,
                status = status
            )
        )
    }

    fun addComment(comment: String) {
        comments.add(
            PostComment(
                post = this,
                comment = comment
            )
        )
    }
}

interface PostRepository : CrudRepository<Post, Long> {

}