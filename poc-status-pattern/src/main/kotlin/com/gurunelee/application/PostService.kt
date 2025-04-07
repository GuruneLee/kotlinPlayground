package com.gurunelee.application

import com.gurunelee.domain.CommentType
import com.gurunelee.domain.Post
import com.gurunelee.domain.PostRepository
import com.gurunelee.domain.PostStatus
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository) {
    fun createPosts() {
        val post = Post.newInstance("content").apply {
            this.addComment("review 1", CommentType.REVIEW)
            this.addComment("review 2", CommentType.REVIEW)
        }
        postRepository.save(post)
    }

    fun getPosts(): List<PostResponse> {
        return postRepository.findAll().map { it ->
            PostResponse(
                postId = it.id,
                comments = it.comments.map { comment -> CommentResponse(comment.id, comment.comment) },
                status = it.statusHandler
            )
        }
    }

    fun updatePost(postId: Long, title: String) {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.updateTitle(title)
    }

    fun publishPost(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.publish()
    }

    fun archivePost(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        post.archive()
    }
}

class PostResponse(
    val postId: Long,
    val comments: List<CommentResponse>,
    val status: PostStatus,
)

class CommentResponse(
    val commentId: Long,
    val comment: String
)