package com.gurunelee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaPlaygroundApplication

fun main(args: Array<String>) {
    val context = runApplication<JpaPlaygroundApplication>(*args)
}
