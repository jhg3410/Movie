package com.jik.feature.popular

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var page = FIRST_PAGE

    val popularMovies = mutableStateListOf<Movie>()
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(page) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> _uiState.value = uiState

                    is UiState.Error -> _uiState.value = uiState

                    is UiState.Success -> {
                        _uiState.value = UiState.Success(Unit)
                        popularMovies.addAll(uiState.data)
                        page++
                    }
                }
            }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}