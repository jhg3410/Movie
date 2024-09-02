package com.jik.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.MovieInfo
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.toUiState
import com.jik.feature.detail.navigation.MovieArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId = MovieArgs(savedStateHandle).movieId
    private val _detailUiState: MutableStateFlow<UiState<MovieInfo>> =
        MutableStateFlow(UiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        getMovieInfo()
    }

    private fun getMovieInfo() {
        _detailUiState.value = UiState.Loading
        viewModelScope.launch {
            movieRepository.getMovieInfo(movieId).toUiState().run {
                _detailUiState.value = this
            }
        }
    }

    fun onRetry() {
        getMovieInfo()
    }
}