package com.jik.feature.popular

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var page = FIRST_PAGE

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    val popularMovies = mutableStateListOf<Movie>()

    fun getPopularMovies() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            movieRepository.getPopularMovies(page).run {
                fold(
                    onSuccess = { movies ->
                        _uiState.value = UiState.Success(Unit)
                        popularMovies.addAll(movies)
                        page++
                    },
                    onFailure = { throwable ->
                        _uiState.value = UiState.Error(throwable)
                    }
                )
            }
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}