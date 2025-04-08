package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class Post private constructor(
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
    private val _statuses: MutableSet<PostStatusEntity> = mutableSetOf()
    private val status: PostStatusEntity
        get() = _statuses.firstOrNull()
            ?: throw IllegalStateException("PostStatusEnum[${id}] not found")
    val statusHandler: PostStatusHandler get() = status.toStatusHandler()
    val currentStatus: PostStatusEnum get() = status.status

    fun addComment(comment: String, commentType: CommentType) {
        statusHandler.addComment(
            comment = PostComment(
                post = this,
                comment = comment,
                commentType = commentType
            ),
            post = this,
        )
    }

    fun updateTitle(title: String) {
        statusHandler.write(title, this)
    }

    fun publish() {
        statusHandler.publish()
        status.updateStatus(PostStatusEnum.PUBLISHED)
    }

    fun archive() {
        statusHandler.archive()
        status.updateStatus(PostStatusEnum.ARCHIVED)
    }

    companion object {
        fun newInstance(title: String): Post {
            return Post(title).apply {
                this._statuses.add(
                    PostStatusEntity(
                        post = this,
                        status = PostStatusEnum.DRAFT
                    )
                )
            }
        }
    }
}

interface PostRepository : CrudRepository<Post, Long> {

}