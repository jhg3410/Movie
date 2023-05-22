package com.jik.feature.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.core.ui.state.getUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val popularUiState: StateFlow<UiState<List<Movie>>> = getUiStateFlow(viewModelScope) {
        movieRepository.getPopularMovies()
    }
}