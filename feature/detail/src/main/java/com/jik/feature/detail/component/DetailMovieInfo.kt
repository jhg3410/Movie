package com.jik.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.jik.core.designsystem.icon.IconColor
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.core.model.MovieInfo
import com.jik.core.ui.util.MovieGenreUtils

@Composable
internal fun DetailMovieInfo(movieInfo: MovieInfo) {
    val horizontalPaddingModifier = Modifier.padding(horizontal = 16.dp)

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Title(
            modifier = horizontalPaddingModifier,
            title = movieInfo.title
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReleaseDateAndRating(
            modifier = horizontalPaddingModifier,
            movieInfo = movieInfo
        )
        Genres(genres = movieInfo.genres)
        Spacer(modifier = Modifier.height(20.dp))
        Overview(
            modifier = horizontalPaddingModifier,
            overview = movieInfo.overview
        )
        Spacer(modifier = Modifier.height(12.dp))
        Cast(cast = movieInfo.cast)
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ReleaseDateAndRating(
    modifier: Modifier = Modifier,
    movieInfo: MovieInfo
) {
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




