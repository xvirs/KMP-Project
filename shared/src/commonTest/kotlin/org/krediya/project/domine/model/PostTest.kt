package org.krediya.project.domine.model

import org.krediya.project.domain.model.Post
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PostTest {

    @Test
    fun `Post data class has correct properties`() {
        // Given
        val userId = 1
        val id = 123
        val title = "Test Title"
        val body = "Test Body Content"

        // When
        val post = Post(userId, id, title, body)

        // Then
        assertEquals(userId, post.userId)
        assertEquals(id, post.id)
        assertEquals(title, post.title)
        assertEquals(body, post.body)
    }

    @Test
    fun `Post equals works correctly`() {
        // Given
        val post1 = Post(1, 123, "Title", "Body")
        val post2 = Post(1, 123, "Title", "Body")
        val post3 = Post(2, 123, "Title", "Body")

        // Then
        assertEquals(post1, post2)
        assertNotEquals(post1, post3)
    }

    @Test
    fun `Post copy works correctly`() {
        // Given
        val original = Post(1, 123, "Original Title", "Original Body")

        // When
        val copied = original.copy(title = "New Title")

        // Then
        assertEquals(original.userId, copied.userId)
        assertEquals(original.id, copied.id)
        assertEquals("New Title", copied.title)
        assertEquals(original.body, copied.body)
    }
}