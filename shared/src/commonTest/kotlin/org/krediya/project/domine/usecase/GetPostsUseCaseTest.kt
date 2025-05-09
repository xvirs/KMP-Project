package org.krediya.project.domine.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.domain.model.Post
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.util.StatusResult
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetPostsUseCaseTest {

    private lateinit var postRepository: PostRepository
    private lateinit var getPostsUseCase: GetPostsUseCase

    @BeforeTest
    fun setup() {
        postRepository = mockk()
        getPostsUseCase = GetPostsUseCase(postRepository)
    }

    @Test
    fun `invoke should return success with posts when repository returns success`() = runTest {
        // Given
        val posts = listOf(
            Post(userId = 1, id = 1, title = "Post 1", body = "Body 1"),
            Post(userId = 1, id = 2, title = "Post 2", body = "Body 2")
        )
        val expectedResult = StatusResult.Success(posts)

        coEvery { postRepository.getPosts() } returns expectedResult

        // When
        val result = getPostsUseCase()

        // Then
        assertTrue(result is StatusResult.Success)
        assertEquals(posts, (result as StatusResult.Success).data)
        coVerify(exactly = 1) { postRepository.getPosts() }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Given
        val errorMessage = "Error fetching posts"
        val expectedResult = StatusResult.Error(errorMessage)

        coEvery { postRepository.getPosts() } returns expectedResult

        // When
        val result = getPostsUseCase()

        // Then
        assertTrue(result is StatusResult.Error)
        assertEquals(errorMessage, (result as StatusResult.Error).message)
        coVerify(exactly = 1) { postRepository.getPosts() }
    }
}