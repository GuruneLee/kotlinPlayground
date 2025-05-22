package com.gurunelee.interfaces

import com.gurunelee.application.PostResponse
import com.gurunelee.application.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(val postService: PostService) {
    @GetMapping("/posts")
    fun getPosts(): List<PostResponse> {
        return postService.getPosts()
    }
}