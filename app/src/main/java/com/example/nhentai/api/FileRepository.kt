package com.example.nhentai.api

import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path


class FileRepository {
    fun create(path: Path, fileName: String?) {
        val filePath: Path = path.resolve(fileName)
        try {
            Files.createFile(filePath)
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
    }

    fun read(path: Path?): String {
        return try {
            String(Files.readAllBytes(path))
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
    }

    fun update(path: Path?, newContent: String): String {
        return try {
            Files.write(path, newContent.toByteArray())
            newContent
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
    }

    fun delete(path: Path?) {
        try {
            Files.deleteIfExists(path)
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
    }
}