package com.jik.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var popularPage = FIRST_PAGE

    val homeUiState = mutableStateOf<UiState<Unit>>(UiState.Loading)
    val popularMovies = mutableStateListOf<Movie>()
    val mainMovie = MutableStateFlow<Movie?>(null)

    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(popularPage) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        homeUiState.value = UiState.Loading
                    }
                    is UiState.Error -> {
                        homeUiState.value = UiState.Error(uiState.throwable)
                    }
                    is UiState.Success -> {
                        popularMovies.addAll(uiState.data)
                        if (popularPage == FIRST_PAGE) setMainMovie()
                        popularPage++
                    }
                }
            }
    }

    private fun setMainMovie() {
        popularMovies.toList().shuffled().firstOrNull()?.let {
            mainMovie.value = it
        }
    }


    companion object {
        private const val FIRST_PAGE = 1
    }
}