package com.jik.feature.detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jik.core.designsystem.component.LoadingWheel
import com.jik.core.designsystem.component.Refresh
import com.jik.core.model.MovieInfo
import com.jik.core.ui.state.UiState


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
                detailUiState = detailUiState.data,
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
    detailUiState: MovieInfo,
    modifier: Modifier = Modifier
) {
    Log.d("DetailScreenContent", "detailUiState: $detailUiState")
}