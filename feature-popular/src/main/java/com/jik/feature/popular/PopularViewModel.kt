package com.jik.feature.popular

import androidx.lifecycle.ViewModel
import com.jik.common.ui.UiState
import com.jik.common.ui.getUiStateFlow
import com.jik.core.data.repository.MovieRepository
import com.jik.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val popularMovieFlow: Flow<UiState<List<Movie>>> = getUiStateFlow {
        movieRepository.getPopularMovies()
    }
}