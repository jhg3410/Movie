package com.jik.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jik.core.data.repository.MovieRepository
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import com.jik.feature.detail.navigation.MovieArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    movieRepository: MovieRepository
) : ViewModel() {

    private val movieId = MovieArgs(savedStateHandle).movieId

    private val retry = MutableStateFlow(false)

    val detailUiState = retry.flatMapLatest {
        getUiStateFlow {
            movieRepository.getMovieInfo(movieId)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState.Loading
    )

    fun onRetry() {
        retry.value = !retry.value
    }
}