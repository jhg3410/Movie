package com.jik.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jik.core.designsystem.component.*
import com.jik.core.designsystem.theme.sansita
import com.jik.core.model.Movie
import com.jik.core.ui.pagination.Pageable
import com.jik.core.ui.state.UiState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    TransparentStatusBar()

    val mainMovie = homeViewModel.mainMovie.collectAsStateWithLifecycle().value

    Box(modifier = modifier) {
        HomeScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            homeUiState = homeViewModel.homeUiState.value,
            mainMovie = mainMovie ?: Movie.EMPTY,
            popularMovies = homeViewModel.popularMovies,
            onLoadMore = homeViewModel::getPopularMovies,
            onRetry = homeViewModel::getPopularMovies,
        )
        HomeScreenTopBar()
    }
}


@Composable
fun TransparentStatusBar() {

    val systemUiController = rememberSystemUiController()
    val defaultColor = MaterialTheme.colorScheme.background
    val darkIcons = isSystemInDarkTheme().not()

    DisposableEffect(Unit) {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = darkIcons)
        onDispose {
            systemUiController.setStatusBarColor(color = defaultColor)
        }
    }
}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeUiState: UiState<Unit>,
    mainMovie: Movie,
    popularMovies: List<Movie>,
    onLoadMore: suspend () -> Unit,
    onRetry: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier) {
        HomeScreenTopContent(mainMovie = mainMovie)
        HomeScreenPopularContent(popularMovies = popularMovies, onLoadMore = onLoadMore)
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
fun HomeScreenTopContent(
    mainMovie: Movie,
    modifier: Modifier = Modifier,
) {

    val colorStops = arrayOf(
        0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
        0.3f to Color.Transparent,
        0.7f to Color.Transparent,
        1.0f to MaterialTheme.colorScheme.background,
    )

    Surface(
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        PosterCard(
            posterPath = mainMovie.getPosterUrl(),
            modifier = Modifier
                .aspectRatio(1f / 1.2f)
                .fillMaxSize(),
            roundedCornerSize = 0.dp,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .aspectRatio(1f / 1.2f)
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        )
    }
}


@Composable
fun HomeScreenPopularContent(
    modifier: Modifier = Modifier,
    popularMovies: List<Movie>,
    onLoadMore: suspend () -> Unit,
) {

    val popularPosterUrls = popularMovies.map { it.getPosterUrl() }

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
            fontFamily = sansita
        )

        LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(items = popularPosterUrls) { posterUrl ->
                PosterCard(
                    posterPath = posterUrl,
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(1f / 1.5f),
                    roundedCornerSize = 16.dp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    modifier: Modifier = Modifier,
) {
    MovieTopAppBar(
        modifier = modifier,
        titleRes = R.string.home,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.5f
            )
        )
    )
}