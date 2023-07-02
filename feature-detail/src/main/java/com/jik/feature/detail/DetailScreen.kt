package com.jik.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.PosterCard
import com.jik.core.designsystem.component.Refresh
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.core.designsystem.theme.IconColor
import com.jik.core.model.MovieInfo
import com.jik.core.ui.state.UiState
import com.jik.core.ui.util.MovieGenreUtils


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val detailUiState = viewModel.detailUiState.collectAsStateWithLifecycle().value

    when (detailUiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingWheel(
                    circleSize = 40.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is UiState.Success -> {
            DetailScreenContent(
                movieInfo = detailUiState.data,
                modifier = modifier
            )
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Refresh(
                    size = 40.dp,
                    onClick = viewModel::onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        PosterCard(
            posterPath = movieInfo.getPosterUrl(),
            modifier = Modifier.aspectRatio(2f / 3f),
            roundedCornerSize = 0.dp
        )

        MovieInformation(movieInfo = movieInfo)
    }
}

@Composable
private fun MovieInformation(movieInfo: MovieInfo) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = movieInfo.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        ReleaseDateAndRating(movieInfo = movieInfo)

        Genres(movieInfo = movieInfo)

        Text(
            text = movieInfo.overview,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
private fun ReleaseDateAndRating(movieInfo: MovieInfo) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = movieInfo.getReleaseDateString(),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelMedium
        )

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Icon(
            imageVector = MovieIcons.Star,
            tint = IconColor.Star,
            modifier = Modifier.size(18.dp),
            contentDescription = "Rating",
        )
        Text(
            text = movieInfo.rating.toString(),
            color = IconColor.Star,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun Genres(movieInfo: MovieInfo) {
    LazyRow(
        modifier = Modifier.padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = movieInfo.genres) { genre ->
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(MovieGenreUtils.getGenreColor(genre.name)),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(all = 8.dp),
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}