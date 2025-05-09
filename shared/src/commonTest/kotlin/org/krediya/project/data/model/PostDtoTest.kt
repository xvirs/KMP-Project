package org.krediya.project.data.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PostDtoTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `test PostDto serialization`() {
        // Given
        val post = PostDto(
            userId = 1,
            id = 123,
            title = "Test Title",
            body = "Test Body Content"
        )

        // When
        val jsonString = json.encodeToString(post)

        // Then
        val expectedJson = """{"userId":1,"id":123,"title":"Test Title","body":"Test Body Content"}"""
        assertEquals(expectedJson, jsonString)
    }

    @Test
    fun `test PostDto deserialization`() {
        // Given
        val jsonString = """{"userId":1,"id":123,"title":"Test Title","body":"Test Body Content"}"""

        // When
        val post = json.decodeFromString<PostDto>(jsonString)

        // Then
        assertEquals(1, post.userId)
        assertEquals(123, post.id)
        assertEquals("Test Title", post.title)
        assertEquals("Test Body Content", post.body)
    }

    @Test
    fun `test PostDto deserialization with extra fields`() {
        // Given
        val jsonString = """{"userId":1,"id":123,"title":"Test Title","body":"Test Body Content","extraField":"should be ignored"}"""

        // When
        val post = json.decodeFromString<PostDto>(jsonString)

        // Then
        assertEquals(1, post.userId)
        assertEquals(123, post.id)
        assertEquals("Test Title", post.title)
        assertEquals("Test Body Content", post.body)
    }
}