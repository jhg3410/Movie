package com.jik.common.ui.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jik.common.ui.theme.MovieTheme

@Composable
fun PosterCard(
    posterPath: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholder: Painter? = null
) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = posterPath,
            placeholder = placeholder,
            contentDescription = contentDescription,
            modifier = Modifier.clickable { onClick() }
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