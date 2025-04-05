package com.gurunelee

import com.gurunelee.domain.Post
import com.gurunelee.domain.PostComment
import com.gurunelee.domain.PostCommentRepository
import com.gurunelee.domain.PostRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
 class PostCommentRepositoryTest {
    @Autowired
    lateinit var postRepository: PostRepository

  @Autowired
    lateinit var postCommentRepository: PostCommentRepository

    @Test
    fun `save post comment`() {
        // given
        val post = postRepository.save(Post("content"))
        val comment = PostComment(post, "comment")

        // when
        val savedComment = postCommentRepository.save(comment)

        // then
        assertNotNull(savedComment.id)
    }
 }