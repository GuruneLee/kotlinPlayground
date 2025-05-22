package com.gurunelee

import com.gurunelee.domain.*
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
        val post = Post.newInstance( "content")

        // when
        val savedPost = postRepository.save(post)

        // then
        assertNotNull(savedPost.id)
    }
}