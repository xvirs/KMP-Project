package org.krediya.project.data.repository

import kotlinx.coroutines.test.runTest
import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.data.model.PostDto
import org.krediya.project.util.StatusResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertIs

class PostRepositoryImplTest {

    @Test
    fun `getPosts should return mapped posts when datasource returns success`() = runTest {
        // Arrange
        val mockDataSource = MockPostsDataSource()
        val mockPosts = listOf(
            PostDto(userId = 1, id = 1, title = "Test Title 1", body = "Test Body 1"),
            PostDto(userId = 2, id = 2, title = "Test Title 2", body = "Test Body 2")
        )
        mockDataSource.mockResult = StatusResult.Success(mockPosts)

        val repository = PostRepositoryImpl(mockDataSource)

        // Act
        val result = repository.getPosts()

        // Assert
        assertIs<StatusResult.Success<List<*>>>(result)
        val posts = (result as StatusResult.Success).data
        assertEquals(2, posts.size)
        assertEquals(1, posts[0].id)
        assertEquals("Test Title 1", posts[0].title)
        assertEquals("Test Body 1", posts[0].body)
        assertEquals(2, posts[1].id)
        assertEquals("Test Title 2", posts[1].title)
    }

    @Test
    fun `getPosts should return error when datasource returns error`() = runTest {
        // Arrange
        val mockDataSource = MockPostsDataSource()
        mockDataSource.mockResult = StatusResult.Error("Test error")

        val repository = PostRepositoryImpl(mockDataSource)

        // Act
        val result = repository.getPosts()

        // Assert
        assertIs<StatusResult.Error>(result)
        assertEquals("Test error", (result as StatusResult.Error).message)
    }

    private class MockPostsDataSource : PostsDataSourceInterface {
        var mockResult: StatusResult<List<PostDto>> = StatusResult.Success(emptyList())

        override suspend fun getPosts(): StatusResult<List<PostDto>> = mockResult
    }
}
