package com.gurunelee

import com.gurunelee.domain.*
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
        val post = postRepository.save(Post.newInstance("content"))
        val comment = PostComment(post, "comment", CommentType.REVIEW)

        // when
        val savedComment = postCommentRepository.save(comment)

        // then
        assertNotNull(savedComment.id)
    }
 }