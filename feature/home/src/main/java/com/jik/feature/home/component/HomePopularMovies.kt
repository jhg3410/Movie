package com.jik.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.PosterCard
import com.jik.core.designsystem.theme.MovieFontFamily
import com.jik.core.model.Movie
import com.jik.core.ui.pagination.Pageable

@Composable
internal fun HomePopularMovies(
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