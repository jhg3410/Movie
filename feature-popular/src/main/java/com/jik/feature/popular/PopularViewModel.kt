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

    private val page = MutableStateFlow(FIRST_PAGE)

    val popularUiStates = mutableStateListOf<PopularUiState>()


    suspend fun getPopularMovies() {
        getUiStateFlow { movieRepository.getPopularMovies(page.value) }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        popularUiStates.add(PopularUiState.Loading)
                    }
                    is UiState.Error -> {
                        popularUiStates.add(PopularUiState.Error(uiState.throwable))
                    }
                    is UiState.Success -> {
                        popularUiStates.addAll(uiState.data.map { PopularUiState.Data(it) })
                        page.value++
                    }
                }
            }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}


sealed interface PopularUiState {

    object Loading : PopularUiState

    data class Data(val movie: Movie) : PopularUiState

    data class Error(val throwable: Throwable) : PopularUiState
}
