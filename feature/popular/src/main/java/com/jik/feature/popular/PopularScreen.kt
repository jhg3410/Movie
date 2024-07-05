package com.jik.feature.popular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.*
import com.jik.core.model.Movie
import com.jik.core.ui.pagination.Pageable
import com.jik.core.ui.state.UiState
import com.jik.feature.popular.component.PopularTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(
    modifier: Modifier = Modifier,
    popularViewModel: PopularViewModel = hiltViewModel(),
    onPosterClick: (Long) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by popularViewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        PopularTopBar(scrollBehavior = scrollBehavior)
        Content(
            modifier = Modifier.weight(1f),
            popularMovies = popularViewModel.popularMovies,
            uiState = uiState,
            onLoadMore = popularViewModel::getPopularMovies,
            onRetry = popularViewModel::getPopularMovies,
            onPosterClick = onPosterClick,
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: UiState<Unit>,
    popularMovies: List<Movie>,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onPosterClick: (Long) -> Unit,
) {
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
        items(popularMovies) { movie ->
            PosterCard(
                posterPath = movie.getPosterUrl(),
                modifier = Modifier.aspectRatio(2 / 3f),
                onClick = { onPosterClick(movie.id) },
                contentScale = ContentScale.Crop,
            )
        }

        val stateItem: (content: @Composable (Modifier) -> Unit) -> Unit = { content ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(contentAlignment = Alignment.Center) {
                    content(Modifier.padding(top = 16.dp, bottom = 16.dp + NavigationBarCornerSize))
                }
            }
        }

        when (uiState) {
            is UiState.Loading -> {
                stateItem { modifier ->
                    LoadingWheel(modifier)
                }
            }

            is UiState.Error -> {
                stateItem { modifier ->
                    Refresh(
                        modifier = modifier,
                        onClick = { onRetry() }
                    )
                }
            }

            is UiState.Success -> Unit
        }
    }
}