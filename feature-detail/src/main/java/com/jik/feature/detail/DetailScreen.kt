package com.jik.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun DetailScreen(
    movieId: Long,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel()
) {


    Box(modifier = modifier) {
        Text(
            text = "Detail Screen${movieId}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}