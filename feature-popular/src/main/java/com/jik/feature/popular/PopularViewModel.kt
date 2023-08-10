package com.jik.feature.popular

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var page = FIRST_PAGE

    val popularUiStates = mutableStateListOf<UiState<Movie>>()

    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(page) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        popularUiStates.add(uiState)
                    }
                    is UiState.Error -> {
                        popularUiStates.add(uiState)
                    }
                    is UiState.Success -> {
                        popularUiStates.addAll(uiState.data.map { UiState.Success(it) })
                        page++
                    }
                }
            }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}