package com.jik.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.*
import com.jik.core.designsystem.theme.MovieFontFamily
import com.jik.core.model.Movie
import com.jik.core.ui.pagination.Pageable
import com.jik.core.ui.state.UiState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {

    val mainMovie = homeViewModel.mainMovie.collectAsStateWithLifecycle().value

    Box(modifier = modifier) {
        Content(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            homeUiState = homeViewModel.homeUiState.value,
            mainMovie = mainMovie ?: Movie.EMPTY,
            popularMovies = homeViewModel.popularMovies,
            onLoadMore = homeViewModel::getPopularMovies,
            onRetry = homeViewModel::getPopularMovies,
            onPosterClick = onPosterClick
        )
        TopBar()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    homeUiState: UiState<Unit>,
    mainMovie: Movie,
    popularMovies: List<Movie>,
    onLoadMore: suspend () -> Unit,
    onRetry: suspend () -> Unit,
    onPosterClick: (Long) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier) {
        TopContent(
            mainMovie = mainMovie,
            onPosterClick = onPosterClick,
        )
        PopularContent(
            popularMovies = popularMovies,
            onLoadMore = onLoadMore,
            onPosterClick = onPosterClick
        )
        Spacer(modifier = Modifier.padding(bottom = NavigationBarCornerSize))
    }

    if (homeUiState is UiState.Loading && mainMovie == Movie.EMPTY) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingWheel(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (homeUiState is UiState.Error) {
        Box(modifier = Modifier.fillMaxSize()) {
            Refresh(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    coroutineScope.launch {
                        onRetry()
                    }
                }
            )
        }
    }
}


@Composable
private fun TopContent(
    mainMovie: Movie,
    modifier: Modifier = Modifier,
    onPosterClick: (Long) -> Unit,
) {

    GradientPosterCard(
        posterPath = mainMovie.getPosterUrl(),
        modifier = modifier
            .padding(bottom = 16.dp)
            .aspectRatio(1f / 1.2f)
            .fillMaxSize(),
        clickable = mainMovie != Movie.EMPTY,
        onClick = { onPosterClick(mainMovie.id) },
        roundedCornerSize = 0.dp,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
    )
}


@Composable
fun PopularContent(
    modifier: Modifier = Modifier,
    popularMovies: List<Movie>,
    onLoadMore: suspend () -> Unit,
    onPosterClick: (Long) -> Unit,
) {


    val lazyListState = rememberLazyListState().apply {
        Pageable(onLoadMore = onLoadMore)
    }

    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "Popular",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = MovieFontFamily.Sansita
        )

        LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(items = popularMovies) { movie ->
                PosterCard(
                    posterPath = movie.getPosterUrl(),
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(1f / 1.5f),
                    onClick = { onPosterClick(movie.id) },
                    roundedCornerSize = 16.dp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    MovieTopAppBar(
        titleRes = com.jik.core.ui.R.string.logo,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.0f
            )
        ),
        titleStyle = MaterialTheme.typography.displayMedium,
        titleFontFamily = MovieFontFamily.LilitaOne,
    )
}