package com.gurunelee.application

import com.gurunelee.domain.PostStatusEnum
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class PostServiceTest {
    @Autowired
    lateinit var postService: PostService

    @AfterEach
    fun tearDown() {
        postService.postRepository.deleteAll()
    }

    @Test
    fun getPost() {
        // given
        val postId = postService.createPosts().postId

        // when
        val post = postService.getPost(postId)

        // then
        println(post)
        assertEquals(postId, post.postId)
    }

    @Test
    fun updatePost() {
        // given
        val post = postService.createPosts()

        // when
        val updatedPost = postService.updatePost(post.postId, "Updated title")

        // then
        assertEquals("Updated title", updatedPost.title)
        assertEquals(post.postId, updatedPost.postId)
        assertEquals(post.comments, updatedPost.comments)
        assertEquals(PostStatusEnum.DRAFT, updatedPost.status)
    }

    @Test
    fun publishPost() {
        // given
        val post = postService.createPosts()

        // when
        val publishedPost = postService.publishPost(post.postId)

        // then
        assertEquals(PostStatusEnum.PUBLISHED, publishedPost.status)
    }

    @Test
    fun archivePost() {
        // given
        val post = postService.createPosts()
        postService.publishPost(post.postId)

        // when
        val archivedPost = postService.archivePost(post.postId)

        // then
        assertEquals(PostStatusEnum.ARCHIVED, archivedPost.status)
    }
}