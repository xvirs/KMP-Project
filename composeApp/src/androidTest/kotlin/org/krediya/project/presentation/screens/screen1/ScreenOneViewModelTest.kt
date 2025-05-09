package org.krediya.project.presentation.screens.screen1

import org.junit.Before
import org.junit.Test
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.presentation.utils.UIState
import kotlin.test.assertTrue

class ScreenOneViewModelSimpleTest {

    private lateinit var viewModel: ScreenOneViewModel

    @Before
    fun setup() {
        // Usar un constructor fake para probar
        val mockGetPostsUseCase = FakeGetPostsUseCase(
            postRepository = TODO()
        )
        viewModel = ScreenOneViewModel(mockGetPostsUseCase)
    }

    @Test
    fun testInitialStateIsLoading() {
        val initialState = viewModel.uiState.value
        assertTrue(initialState is UIState.Loading)
    }

    // Clase falsa para probar
    class FakeGetPostsUseCase(postRepository: PostRepository) : GetPostsUseCase(postRepository) {
        // Implementar según tus necesidades
    }
}