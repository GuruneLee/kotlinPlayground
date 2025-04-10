package com.gurunelee.domain

interface PostCommand {
    fun execute()
}

class PostWriteCommand(
    private val post: Post,
    private val title: String,
) : PostCommand {
    override fun execute() {
        post.title = title
    }
}

class PostPublishCommand(
    private val post: Post,
) : PostCommand {
    override fun execute() {
        return
    }
}

class PostArchiveCommand(
    private val post: Post,
) : PostCommand {
    override fun execute() {
        return
    }
}

class PostAddCommentCommand(
    private val post: Post,
    private val commentType: CommentType,
    private val comment: String,
) : PostCommand {
    override fun execute() {
        val postComment = PostComment(
            post = post,
            comment = comment,
            commentType = commentType,
        )

        when (commentType) {
            CommentType.REVIEW -> post.comments.add(postComment)
            CommentType.REPLY -> if (post.comments.any { it.commentType == CommentType.REVIEW }) {
                post.comments.add(postComment)
            } else {
                throw IllegalStateException("Post can not have any reply comment without review comment.")
            }
        }
    }
}
