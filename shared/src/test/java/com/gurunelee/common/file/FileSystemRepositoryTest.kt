package com.gurunelee.common.file

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import kotlin.io.path.absolutePathString
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class FileSystemRepositoryTest {
    val fileSystemRepository = FileSystemRepository(TEST_FILE_SYSTEM_ROOT)

    @Test
    fun `test findAll returns non-empty list`() {
        val saved = fileSystemRepository.saveFile(
            name = "test.txt",
            content = "This is a test file".byteInputStream()
        )
        val paths = fileSystemRepository.findAll()

        println(paths.map { it.absolutePathString() })
    }

    @AfterEach
    fun cleanUp() {
        Files.list(TEST_FILE_SYSTEM_ROOT.toPath()).forEach { file ->
            Files.deleteIfExists(file)
        }
    }

    companion object {
        val TEST_FILE_SYSTEM_ROOT = "src/test/resources/files"
    }
}

