package com.jik.feature.popular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.component.PosterCard
import com.jik.core.designsystem.component.Refresh
import com.jik.core.ui.pagination.Pageable
import kotlinx.coroutines.launch


@Composable
fun PopularScreen(
    onPosterCardClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    popularViewModel: PopularViewModel = hiltViewModel()
) {

    PopularScreenContent(
        uiStates = popularViewModel.uiStates,
        onLoadMore = popularViewModel::getPopularMovies,
        onRetry = popularViewModel::getPopularMovies,
        onPosterCardClick = onPosterCardClick,
        modifier = modifier,
    )
}


@Composable
fun PopularScreenContent(
    uiStates: List<PopularUiState>,
    onLoadMore: suspend () -> Unit,
    onRetry: suspend () -> Unit,
    onPosterCardClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    val popularScreenHeight = LocalConfiguration.current.screenHeightDp.dp - MovieTopAppBar.height

    val lazyGridState = rememberLazyGridState().apply {
        Pageable(onLoadMore = onLoadMore)
    }

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        uiStates.forEachIndexed { index, uiState ->
            when (uiState) {
                is PopularUiState.Data -> {
                    item {
                        PosterCard(
                            posterPath = uiState.movie.getPosterUrl(),
                            modifier = Modifier
                                .sizeIn(minWidth = 160.dp, minHeight = 240.dp)
                                .aspectRatio(2f / 3f),
                            onClick = { onPosterCardClick(uiState.movie.id) }
                        )
                    }
                }
                is PopularUiState.Loading -> {
                    if (index != uiStates.size - 1) return@forEachIndexed

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.height(popularScreenHeight)
                        ) {
                            LoadingWheel(
                                modifier = Modifier.align(Alignment.Center),
                                circleSize = 40.dp
                            )
                        }
                    }
                }
                is PopularUiState.Error -> {
                    if (index != uiStates.size - 1) return@forEachIndexed

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.height(popularScreenHeight)
                        ) {
                            Refresh(
                                modifier = Modifier.align(Alignment.Center),
                                size = 40.dp,
                                onClick = {
                                    coroutineScope.launch {
                                        onRetry()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}