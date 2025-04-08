package com.gurunelee.domain

enum class PostAction {
    WRITE,
    PUBLISH,
    ARCHIVE,
    ADD_COMMENT,
}

interface PostActionPolicy {
    fun isActionAvailable(action: PostAction, post: Post, privileges: Set<String>): Boolean
}

class DraftPostActionPolicy : PostActionPolicy {
    override fun isActionAvailable(action: PostAction, post: Post, privileges: Set<String>): Boolean {
        return when(action) {
            PostAction.WRITE -> privileges.contains("EDIT")
            PostAction.PUBLISH -> false
            PostAction.ARCHIVE -> false
            PostAction.ADD_COMMENT -> post.comments.any { it.commentType == CommentType.REVIEW }
                    && privileges.contains("COMMENT.ALL")
        }
    }
}

class PublishedPostActionPolicy : PostActionPolicy {
    override fun isActionAvailable(action: PostAction, post: Post, privileges: Set<String>): Boolean {
        return when(action) {
            PostAction.WRITE -> false
            PostAction.PUBLISH -> false
            PostAction.ARCHIVE -> privileges.contains("ARCHIVE")
            PostAction.ADD_COMMENT -> privileges.contains("COMMENT.ALL")
        }
    }
}

class ArchivedPostActionPolicy : PostActionPolicy {
    override fun isActionAvailable(action: PostAction, post: Post, privileges: Set<String>): Boolean {
        return when(action) {
            PostAction.WRITE -> false
            PostAction.PUBLISH -> false
            PostAction.ARCHIVE -> false
            PostAction.ADD_COMMENT -> false
        }
    }
}