package com.gurunelee.domain

interface PostStatus {
    fun write(title: String, post: Post)
    fun publish()
    fun archive()
    fun addComment(comment: PostComment, post: Post)

    fun getActionPolicy(): PostActionPolicy
}

class DraftStatus(
    private val actionPolicy: PostActionPolicy,
) : PostStatus {

    override fun write(title: String, post: Post) {
        post.updateTitle(title)
    }

    override fun publish() {
        throw IllegalStateException("Post is now published.")
    }

    override fun archive() {
        throw IllegalStateException("Post can not be archived.")
    }

    override fun addComment(comment: PostComment, post: Post) {
        when (comment.commentType) {
            CommentType.REVIEW -> throw IllegalStateException("Post is not published yet.")
            CommentType.REPLY -> if (post.comments.any { it.commentType == CommentType.REVIEW }) {
                post.addComment(comment.comment, comment.commentType)
            } else {
                throw IllegalStateException("Post can not have any reply comment without review comment.")
            }
        }
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

class PublishedStatus(
    private val actionPolicy: PostActionPolicy,
) : PostStatus {

    override fun write(title: String, post: Post) {
        throw IllegalStateException("Post is already published.")
    }

    override fun publish() {
        throw IllegalStateException("Post is already published.")
    }

    override fun archive() {
        return
    }

    override fun addComment(comment: PostComment, post: Post) {
        when (comment.commentType) {
            CommentType.REVIEW -> post.addComment(comment.comment, comment.commentType)
            CommentType.REPLY -> throw IllegalStateException("Post can't have any reply comment.")
        }
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

class ArchivedStatus(
    private val actionPolicy: PostActionPolicy,
) : PostStatus {

    override fun write(title: String, post: Post) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun publish() {
        throw IllegalStateException("Post is already archived.")
    }

    override fun archive() {
        throw IllegalStateException("Post is already archived.")
    }

    override fun addComment(comment: PostComment, post: Post) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

fun PostStatusEntity.toStatusHandler(): PostStatus {
    return when (this.status) {
        PostStatusEnum.DRAFT -> DraftStatus(DraftPostActionPolicy())
        PostStatusEnum.PUBLISHED -> PublishedStatus(PublishedPostActionPolicy())
        PostStatusEnum.ARCHIVED -> ArchivedStatus(ArchivedPostActionPolicy())
    }
}