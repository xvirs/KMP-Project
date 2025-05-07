package org.krediya.project.data.mapper

import org.krediya.project.data.model.PostDto
import org.krediya.project.domain.model.Post
import kotlin.test.Test
import kotlin.test.assertEquals

class PostMapperTest {

    @Test
    fun `toDomain should map PostDto to Post correctly`() {
        // Arrange
        val postDto = PostDto(
            userId = 1,
            id = 2,
            title = "Test Title",
            body = "Test Body"
        )

        // Act
        val result = postDto.toDomain()

        // Assert
        assertEquals(1, result.userId)
        assertEquals(2, result.id)
        assertEquals("Test Title", result.title)
        assertEquals("Test Body", result.body)
    }

    @Test
    fun `toDto should map Post to PostDto correctly`() {
        // Arrange
        val post = Post(
            userId = 3,
            id = 4,
            title = "Domain Title",
            body = "Domain Body"
        )

        // Act
        val result = post.toDto()

        // Assert
        assertEquals(3, result.userId)
        assertEquals(4, result.id)
        assertEquals("Domain Title", result.title)
        assertEquals("Domain Body", result.body)
    }

    @Test
    fun `mapping should preserve data integrity in both directions`() {
        // Arrange
        val original = PostDto(
            userId = 5,
            id = 6,
            title = "Original Title",
            body = "Original Body"
        )

        // Act
        val domain = original.toDomain()
        val backToDto = domain.toDto()

        // Assert
        assertEquals(original.userId, backToDto.userId)
        assertEquals(original.id, backToDto.id)
        assertEquals(original.title, backToDto.title)
        assertEquals(original.body, backToDto.body)
    }
}