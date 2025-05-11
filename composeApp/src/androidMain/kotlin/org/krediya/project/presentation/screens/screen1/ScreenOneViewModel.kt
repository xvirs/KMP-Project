package org.krediya.project.presentation.screens.screen1

import org.krediya.project.presentation.utils.UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.presentation.model.PostUI
import org.krediya.project.presentation.model.toUI
import org.krediya.project.util.StatusResult

class ScreenOneViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<PostUI>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<PostUI>>> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            when (val result = getPostsUseCase()) {
                is StatusResult.Success -> {
                    val postUIList = result.data.map { it.toUI() }
                    _uiState.value = UIState.Success(postUIList)
                }
                is StatusResult.Error -> {
                    _uiState.value = UIState.Error(result.message)
                }
            }
        }
    }
}