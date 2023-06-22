package com.jik.feature.popular

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val page = MutableStateFlow(1)

    val popularMovies = mutableStateListOf<Movie>()
    var visibleLoading by mutableStateOf(false)
    var visibleError by mutableStateOf(false)


    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(page.value) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        visibleError = true
                        visibleLoading = false
                    }
                    is UiState.Loading -> {
                        visibleLoading = true
                    }
                    is UiState.Success -> {
                        visibleLoading = false
                        page.value++
                        popularMovies.addAll(uiState.data)
                    }
                }
            }
    }
}