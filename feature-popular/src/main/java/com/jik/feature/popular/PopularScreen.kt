package com.jik.feature.popular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jik.core.designsystem.component.*
import com.jik.core.model.Movie
import com.jik.core.ui.pagination.Pageable
import com.jik.core.ui.state.UiState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(
    modifier: Modifier = Modifier,
    popularViewModel: PopularViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Column(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        TopBar(scrollBehavior = scrollBehavior)

        Content(
            popularUiStates = popularViewModel.popularUiStates,
            onLoadMore = popularViewModel::getPopularMovies,
            onRetry = popularViewModel::getPopularMovies,
            onPosterClick = onPosterClick,
            modifier = modifier,
        )

        Spacer(modifier = Modifier.padding(bottom = NavigationBarCornerSize))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(scrollBehavior: TopAppBarScrollBehavior) {
    MovieTopAppBar(
        titleRes = R.string.popular,
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun Content(
    popularUiStates: List<UiState<Movie>>,
    onLoadMore: suspend () -> Unit,
    onRetry: suspend () -> Unit,
    onPosterClick: (Long) -> Unit,
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
        popularUiStates.forEachIndexed { index, uiState ->
            when (uiState) {
                is UiState.Success -> {
                    item {
                        PosterCard(
                            posterPath = uiState.data.getPosterUrl(),
                            modifier = Modifier
                                .sizeIn(minWidth = 160.dp, minHeight = 240.dp)
                                .aspectRatio(2f / 3f),
                            onClick = { onPosterClick(uiState.data.id) }
                        )
                    }
                }
                is UiState.Loading -> {
                    if (index != popularUiStates.size - 1) return@forEachIndexed

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(modifier = Modifier.height(popularScreenHeight)) {
                            LoadingWheel(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                is UiState.Error -> {
                    if (index != popularUiStates.size - 1) return@forEachIndexed

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(modifier = Modifier.height(popularScreenHeight)) {
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
            }
        }
    }
}