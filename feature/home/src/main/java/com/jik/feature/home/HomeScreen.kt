package com.jik.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.NavigationBarCornerSize
import com.jik.core.designsystem.component.Refresh
import com.jik.core.model.Movie
import com.jik.core.ui.state.UiState
import com.jik.feature.home.component.HomeMainMovie
import com.jik.feature.home.component.HomePopularMovies
import com.jik.feature.home.component.HomeTopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val mainMovie by homeViewModel.mainMovie.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Content(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            uiState = uiState,
            mainMovie = mainMovie ?: Movie.EMPTY,
            popularMovies = homeViewModel.popularMovies,
            onLoadMore = homeViewModel::getPopularMovies,
            onRetry = homeViewModel::getPopularMovies,
            onPosterClick = onPosterClick
        )
        HomeTopBar()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: UiState<Unit>,
    mainMovie: Movie,
    popularMovies: List<Movie>,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onPosterClick: (Long) -> Unit,
) {
    Column(modifier) {
        HomeMainMovie(
            mainMovie = mainMovie,
            onPosterClick = onPosterClick,
        )
        HomePopularMovies(
            popularMovies = popularMovies,
            onLoadMore = onLoadMore,
            onPosterClick = onPosterClick
        )
        Spacer(modifier = Modifier.padding(bottom = NavigationBarCornerSize))
    }

    if (uiState is UiState.Loading && mainMovie == Movie.EMPTY) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingWheel(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (uiState is UiState.Error) {
        Box(modifier = Modifier.fillMaxSize()) {
            Refresh(
                modifier = Modifier.align(Alignment.Center),
                onClick = { onRetry() }
            )
        }
    }
}