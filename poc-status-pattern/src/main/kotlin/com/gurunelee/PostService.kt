package com.gurunelee

import com.gurunelee.domain.Post
import com.gurunelee.domain.PostRepository
import com.gurunelee.domain.PostStatusEnum
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository) {
    fun createPosts() {
        val post = Post("content").apply {
            this.addStatus(PostStatusEnum.DRAFT)
            this.addComment("comment1")
            this.addComment("comment2")
        }
        postRepository.save(post)
    }

    fun getPosts(): List<PostResponse> {
        return postRepository.findAll().map { it ->
            PostResponse(
                postId = it.id,
                comments = it.comments.map { comment -> CommentResponse(comment.id, comment.comment) },
                status = it.status.status
            )
        }
    }
}

class PostResponse(
    val postId: Long,
    val comments: List<CommentResponse>,
    val status: PostStatusEnum,
)

class CommentResponse(
    val commentId: Long,
    val comment: String
)