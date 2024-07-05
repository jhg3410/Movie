package com.jik.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.PosterCard
import com.jik.core.designsystem.component.Refresh
import com.jik.core.model.MovieInfo
import com.jik.core.ui.state.UiState
import com.jik.feature.detail.component.DetailMovieInfo
import com.jik.feature.detail.component.TopBar
import com.jik.lib.videoplayer.VideoPlayer


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val detailUiState = viewModel.detailUiState.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.CREATED
    ).value

    when (detailUiState) {
        is UiState.Loading -> {
            Box(modifier = modifier) {
                LoadingWheel(
                    circleSize = 40.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is UiState.Success -> {
            Column(modifier = modifier) {
                TopBar(
                    modifier = Modifier.fillMaxWidth(),
                    navigateUp = navigateUp
                )
                Content(movieInfo = detailUiState.data)
            }
        }

        is UiState.Error -> {
            Box(modifier = modifier) {
                Refresh(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = viewModel::onRetry,
                    size = 40.dp,
                )
            }
        }
    }
}


@Composable
private fun Content(
    modifier: Modifier = Modifier,
    movieInfo: MovieInfo,
) {
    Column(modifier = modifier) {
        VideoPlayer(
            modifier = Modifier.aspectRatio(500f / 281f),
            videoId = movieInfo.video?.videoId,
            thumbnail = {
                PosterCard(
                    posterPath = movieInfo.getBackdropUrl(),
                    modifier = Modifier.fillMaxSize(),
                    roundedCornerSize = 0.dp,
                    clickable = false,
                )
            },
        )
        Spacer(modifier = Modifier.height(24.dp))
        DetailMovieInfo(movieInfo = movieInfo)
    }
}