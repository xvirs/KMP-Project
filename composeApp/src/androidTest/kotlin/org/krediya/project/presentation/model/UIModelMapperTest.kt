package org.krediya.project.presentation.model

import org.junit.Test
import org.krediya.project.domain.model.Post
import kotlin.test.assertEquals

class UIModelMapperTest {

    @Test
    fun `Post toUI should correctly map domain Post to PostUI`() {
        // Given
        val post = Post(
            userId = 1,
            id = 2,
            title = "Test Title",
            body = "Test Body"
        )

        // When
        val result = post.toUI()

        // Then
        assertEquals(1, result.userId)
        assertEquals(2, result.id)
        assertEquals("Test Title", result.title)
        assertEquals("Test Body", result.body)
    }
}