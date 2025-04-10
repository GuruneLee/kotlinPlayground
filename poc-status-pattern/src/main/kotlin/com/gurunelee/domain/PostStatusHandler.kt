package com.gurunelee.domain

interface PostStatusHandler {
    fun write(postCommand: PostCommand)
    fun publish(postCommand: PostCommand)
    fun archive(postCommand: PostCommand)
    fun addComment(commentType: CommentType, postCommand: PostCommand)

    fun getActionPolicy(): PostActionPolicy
}

class DraftStatusHandler(
    private val actionPolicy: PostActionPolicy,
) : PostStatusHandler {

    override fun write(postCommand: PostCommand) {
        postCommand.execute()
    }

    override fun publish(postCommand: PostCommand) {
        return
    }

    override fun archive(postCommand: PostCommand) {
        throw IllegalStateException("Post can not be archived.")
    }

    override fun addComment(commentType: CommentType, postCommand: PostCommand) {
        when (commentType) {
            CommentType.REVIEW -> throw IllegalStateException("Post is not published yet.")
            CommentType.REPLY -> postCommand.execute()
        }
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}

class PublishedStatusHandler(
    private val actionPolicy: PostActionPolicy,
) : PostStatusHandler {

    override fun write(postCommand: PostCommand) {
        throw IllegalStateException("Post is already published.")
    }

    override fun publish(postCommand: PostCommand) {
        throw IllegalStateException("Post is already published.")
    }

    override fun archive(postCommand: PostCommand) {
        return
    }

    override fun addComment(commentType: CommentType, postCommand: PostCommand) {
        when (commentType) {
            CommentType.REVIEW -> postCommand.execute()
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

    override fun write(postCommand: PostCommand) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun publish(postCommand: PostCommand) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun archive(postCommand: PostCommand) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun addComment(commentType: CommentType, postCommand: PostCommand) {
        throw IllegalStateException("Post is already archived.")
    }

    override fun getActionPolicy(): PostActionPolicy {
        return actionPolicy
    }
}