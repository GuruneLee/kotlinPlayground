package com.gurunelee.domain

interface PostStatusHandler {
    fun write(title: String, post: Post)
    fun publish()
    fun archive()
    fun addComment(comment: PostComment, post: Post)

    fun getActionPolicy(): PostActionPolicy
}

class DraftStatusHandler(
    private val actionPolicy: PostActionPolicy,
) : PostStatusHandler {

    override fun write(title: String, post: Post) {
        post.title = title
    }

    override fun publish() {
        return
    }

    override fun archive() {
        throw IllegalStateException("Post can not be archived.")
    }

    override fun addComment(comment: PostComment, post: Post) {
        when (comment.commentType) {
            CommentType.REVIEW -> throw IllegalStateException("Post is not published yet.")
            CommentType.REPLY -> if (post.comments.any { it.commentType == CommentType.REVIEW }) {
                post.comments.add(comment)
            } else {
                throw IllegalStateException("Post can not have any reply comment without review comment.")
            }
        }
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

class PublishedStatusHandler(
    private val actionPolicy: PostActionPolicy,
) : PostStatusHandler {

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
            CommentType.REVIEW -> post.comments.add(comment)
            CommentType.REPLY -> throw IllegalStateException("Post can't have any reply comment.")
        }
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

class ArchivedStatusHandler(
    private val actionPolicy: PostActionPolicy,
) : PostStatusHandler {

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