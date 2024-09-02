package com.jik.feature.home.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.GradientPosterCard
import com.jik.core.model.Movie

@Composable
internal fun HomeMainMovie(
    mainMovie: Movie,
    modifier: Modifier = Modifier,
    onPosterClick: (Long) -> Unit,
) {
    GradientPosterCard(
        posterPath = mainMovie.getPosterUrl(),
        modifier = modifier
            .padding(bottom = 16.dp)
            .aspectRatio(1f / 1.2f)
            .fillMaxSize(),
        clickable = mainMovie != Movie.EMPTY,
        onClick = { onPosterClick(mainMovie.id) },
        roundedCornerSize = 0.dp,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
    )
}