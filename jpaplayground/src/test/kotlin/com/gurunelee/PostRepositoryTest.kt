package com.gurunelee

import com.gurunelee.domain.Post
import com.gurunelee.domain.PostComment
import com.gurunelee.domain.PostRepository
import com.gurunelee.domain.PostStatusEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PostRepositoryTest {
    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun `test find all posts`() {
        // given
        // when
        val posts = postRepository.findAll().toList()

        // then
        assertEquals(1, posts.size)
    }

    @Test
    fun `save post`() {
        // given
        val post = Post( "content").apply {
            this.addStatus(PostStatusEnum.DRAFT)
            this.addComment("comment1")
            this.addComment("comment2")
        }

        // when
        val savedPost = postRepository.save(post)

        // then
        assertNotNull(savedPost.id)
    }
}