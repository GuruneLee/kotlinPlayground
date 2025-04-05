package com.gurunelee

import com.gurunelee.domain.Post
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(val postService: PostService) {
    @GetMapping("/posts")
    fun getPosts(): List<PostResponse> {
        return postService.getPosts()
    }
}