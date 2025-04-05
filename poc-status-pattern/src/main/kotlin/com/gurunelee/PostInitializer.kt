package com.gurunelee

import com.gurunelee.domain.PostRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PostInitializer(
    private val postService: PostService,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        // Initialize the database with some posts
        postService.createPosts()
    }
}