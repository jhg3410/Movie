package com.jik.core.designsystem.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jik.core.designsystem.theme.MovieTheme

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
                .clickable(enabled = clickable) { onClick() },
            placeholder = placeholder,
            alignment = alignment,
            contentScale = contentScale
        )
    }
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