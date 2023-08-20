package com.jik.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.component.PosterCard
import com.jik.core.designsystem.component.Refresh
import com.jik.core.designsystem.icon.IconColor
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.core.model.MovieInfo
import com.jik.core.ui.state.UiState
import com.jik.core.ui.util.MovieGenreUtils
import com.jik.lib.videoplayer.ui.VideoPlayer


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
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
            Column(modifier = modifier.fillMaxSize()) {
                TopBar(
                    modifier = Modifier.fillMaxWidth(),
                    navigateUp = navigateUp
                )
                Content(
                    movieInfo = detailUiState.data,
                    modifier = modifier
                )
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    MovieTopAppBar(
        titleRes = null,
        modifier = modifier,
        canNavigateBack = true,
        navigateBack = navigateUp
    )
}

@Composable
private fun Content(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        VideoPlayer(
            modifier = Modifier.aspectRatio(500f / 281f),
            Thumbnail = {
                PosterCard(
                    posterPath = movieInfo.getBackdropUrl(),
                    modifier = Modifier.fillMaxSize(),
                    roundedCornerSize = 0.dp,
                    clickable = false,
                )
            },
            videoUrl = movieInfo.video?.videoId ?: "", // todo no video found
        )
        Spacer(modifier = Modifier.height(24.dp))
        MovieInformation(movieInfo = movieInfo)
    }
}

@Composable
private fun MovieInformation(movieInfo: MovieInfo) {

    val horizontalPaddingModifier = Modifier.padding(horizontal = 16.dp)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Title(
            title = movieInfo.title,
            modifier = horizontalPaddingModifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReleaseDateAndRating(
            movieInfo = movieInfo,
            modifier = horizontalPaddingModifier
        )
        Genres(genres = movieInfo.genres)
        Spacer(modifier = Modifier.height(20.dp))
        Overview(
            overview = movieInfo.overview,
            modifier = horizontalPaddingModifier
        )
        Spacer(modifier = Modifier.height(12.dp))
        Cast(cast = movieInfo.cast)
    }
}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ReleaseDateAndRating(movieInfo: MovieInfo, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
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
private fun Genres(genres: List<MovieInfo.Genre>) {
    LazyRow(
        modifier = Modifier.padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = genres) { genre ->
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
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}


@Composable
private fun Overview(overview: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "OVERVIEW",
            color = Color(0xFF8A8A8A),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun Cast(cast: List<MovieInfo.CastItem>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "CAST",
            color = Color(0xFF8A8A8A),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(items = cast) { castItem ->
                CastItem(castItem = castItem)
            }
        }
    }
}

@Composable
private fun CastItem(castItem: MovieInfo.CastItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = castItem.getProfileUrl(),
            contentDescription = "Cast Image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(id = com.jik.core.designsystem.R.drawable.default_profile)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = castItem.name,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = castItem.character,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}