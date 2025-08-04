package com.gurunelee.common.file

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileSystemRepository(
    val fileSystemRoot: String = DEFUALT_FILE_SYSTEM_ROOT
) {
    fun findAll(): List<Path> {
        return Files.list(fileSystemRoot.toPath())
            .toList()
    }

    fun findByName(name: String): Path? {
        val path = fileSystemRoot.toPath().resolve(name)
        return if (Files.exists(path)) path else null
    }

    fun saveFile(name: String, content: InputStream): Path {
        val path = fileSystemRoot.toPath().resolve(name)
        Files.createDirectories(path.parent)
        content.use {
            Files.copy(it, path)
        }

        return path
    }

    companion object {
        val DEFUALT_FILE_SYSTEM_ROOT = "src/main/resources/files"
    }
}


fun String.toPath(): Path {
    return Paths.get(this)
}