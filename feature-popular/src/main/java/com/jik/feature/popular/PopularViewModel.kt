package com.jik.feature.popular

import androidx.compose.runtime.mutableStateListOf
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

    val uiStates = mutableStateListOf<PopularUiState>()


    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(page.value) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        uiStates.add(PopularUiState.Loading)
                    }
                    is UiState.Error -> {
                        uiStates.add(PopularUiState.Error(uiState.throwable))
                    }
                    is UiState.Success -> {
                        page.value++
                        uiState.data.forEach {
                            uiStates.add(PopularUiState.Data(it))
                        }
                    }
                }
            }
    }
}


sealed class PopularUiState {
    object Loading : PopularUiState()

    data class Error(val throwable: Throwable) : PopularUiState()

    data class Data(val movie: Movie) : PopularUiState()
}
