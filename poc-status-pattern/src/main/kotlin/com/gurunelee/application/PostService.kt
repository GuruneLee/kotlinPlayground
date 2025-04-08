package com.gurunelee.application

import com.gurunelee.domain.Post
import com.gurunelee.domain.PostAction
import com.gurunelee.domain.PostRepository
import com.gurunelee.domain.PostStatusEnum
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    val postRepository: PostRepository,
    val privilegeService: PrivilegeService,
) {
    @Transactional
    fun createPosts(): PostResponse {
        val post = Post.newInstance("content").apply {
//            this.addComment("review 1", CommentType.REVIEW)
//            this.addComment("review 2", CommentType.REVIEW)
        }
        return postRepository.save(post).toResponse()
    }

    @Transactional(readOnly = true)
    fun getPosts(): List<PostResponse> {
        return postRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getPost(postId: Long): PostResponse {
        return postRepository.findById(postId).getOrNull()?.toResponse()
            ?: throw IllegalArgumentException("Post not found")
    }

    @Transactional
    fun updatePost(postId: Long, title: String): PostResponse {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.updateTitle(title)

        return post.toResponse()
    }

    @Transactional
    fun publishPost(postId: Long): PostResponse {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.publish()

        return post.toResponse()
    }

    @Transactional
    fun archivePost(postId: Long): PostResponse {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.archive()

        return post.toResponse()
    }

    private fun Post.toResponse(): PostResponse {
        return PostResponse(
            postId = this.id,
            title = this.title,
            comments = this.comments.map { comment -> CommentResponse(comment.id, comment.comment) },
            status = this.currentStatus,
            availableActions = PostAction.entries.map { action ->
                val policy = this.statusHandler.getActionPolicy()
                PostActionAvailability(
                    action = action,
                    available = policy.isActionAvailable(action, this, privilegeService.getPrivileges())
                )
            }.toSet()
        )
    }
}

data class PostResponse(
    val postId: Long,
    val title: String,
    val comments: List<CommentResponse>,
    val status: PostStatusEnum,
    val availableActions: Set<PostActionAvailability>
)

data class PostActionAvailability(
    val action: PostAction,
    val available: Boolean
)

data class CommentResponse(
    val commentId: Long,
    val comment: String
)