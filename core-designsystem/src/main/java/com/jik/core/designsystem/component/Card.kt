package com.jik.core.designsystem.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.util.modifier.clickableSingle

@Composable
fun PosterCard(
    posterPath: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    roundedCornerSize: Dp = 16.dp,
    contentDescription: String? = null,
    placeholder: Painter? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit
) {
    ElevatedCard(
        shape = RoundedCornerShape(roundedCornerSize),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = posterPath,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .clickableSingle(enabled = clickable) { onClick() },
            placeholder = placeholder,
            alignment = alignment,
            contentScale = contentScale
        )
    }
}

@Composable
fun GradientPosterCard(
    posterPath: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    roundedCornerSize: Dp = 16.dp,
    contentDescription: String? = null,
    placeholder: Painter? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    gradientArea: GradientArea = GradientArea.TOP_BOTTOM
) {
    Surface(modifier = modifier) {
        ElevatedCard(
            shape = RoundedCornerShape(roundedCornerSize),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = posterPath,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .clickableSingle(enabled = clickable) { onClick() },
                placeholder = placeholder,
                alignment = alignment,
                contentScale = contentScale
            )
        }

        val colorStopsOfTopBottom = arrayOf(
            0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
            0.3f to Color.Transparent,
            0.7f to Color.Transparent,
            1.0f to MaterialTheme.colorScheme.background,
        )

        val colorStopsOfTop = arrayOf(
            0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
            0.3f to Color.Transparent,
        )

        val colorStopsOfBottom = arrayOf(
            0.7f to Color.Transparent,
            1.0f to MaterialTheme.colorScheme.background,
        )

        val colorStops = when (gradientArea) {
            GradientArea.TOP_BOTTOM -> colorStopsOfTopBottom
            GradientArea.TOP -> colorStopsOfTop
            GradientArea.BOTTOM -> colorStopsOfBottom
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        )
    }
}


enum class GradientArea {
    TOP_BOTTOM,
    TOP,
    BOTTOM,
}


@Preview
@Composable
fun PosterCardPreview() {
    MovieTheme(
        dynamicColor = false
    ) {
        val context = LocalContext.current
        PosterCard(
            posterPath = "https://image.tmdb.org/t/p/w500/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
            modifier = Modifier.size(width = 160.dp, height = 240.dp),
            onClick = { Toast.makeText(context, "hi i`m PosterCard", Toast.LENGTH_SHORT).show() }
        )
    }
}