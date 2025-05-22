package com.gurunelee.domain

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
class Post private constructor(
    @Column(name = "TITLE")
    var title: String,
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
    val statusHandler: PostStatusHandler get() = getStatusHandler(status.status)
    val currentStatus: PostStatusEnum get() = status.status

    fun addComment(comment: String, commentType: CommentType) {
        statusHandler.addComment(
            commentType = commentType,
            postCommand = PostAddCommentCommand(commentType = commentType, post = this, comment = comment),
        )
    }

    fun updateTitle(title: String) {
        statusHandler.write(PostWriteCommand(this, title))
    }

    fun publish() {
        statusHandler.publish(PostPublishCommand(this))
        status.updateStatus(PostStatusEnum.PUBLISHED)
    }

    fun archive() {
        statusHandler.archive(PostArchiveCommand(this))
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

@Repository
interface PostRepository : CrudRepository<Post, Long> {

}

fun getStatusHandler(status: PostStatusEnum): PostStatusHandler {
    return when (status) {
        PostStatusEnum.DRAFT -> DraftStatusHandler(DraftPostActionPolicy())
        PostStatusEnum.PUBLISHED -> PublishedStatusHandler(PublishedPostActionPolicy())
        PostStatusEnum.ARCHIVED -> ArchivedStatusHandler(ArchivedPostActionPolicy())
    }
}

fun getPostPredicate(post: Post): PostPredicate {
    return PostPredicate(
        canReply = { post.comments.any { it.commentType == CommentType.REPLY } }
    )
}