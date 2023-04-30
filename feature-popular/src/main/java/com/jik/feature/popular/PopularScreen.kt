package com.jik.feature.popular

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jik.common.ui.component.PosterCard
import com.jik.core.model.Movie

@Composable
fun PopularScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = popularData) { movie ->
            PosterCard(
                posterPath = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
                modifier = Modifier.size(width = 160.dp, height = 240.dp),
                onClick = { onPosterCardClick(context, movie) }
            )
        }
    }
}

private fun onPosterCardClick(context: Context, movie: Movie) {
    Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
}

private val popularData: List<Movie> = listOf(
    Movie(
        id = 502356,
        title = "The Super Mario Bros. Movie",
        posterPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg"
    ),
    Movie(
        id = 677179,
        title = "Creed III",
        posterPath = "/vJU3rXSP9hwUuLeq8IpfsJShLOk.jpg"
    ),
    Movie(
        id = 76600,
        title = "Avatar: The Way of Water",
        posterPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
    ),
    Movie(
        id = 638974,
        title = "Murder Mystery 2",
        posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
    ),
    Movie(
        id = 638974,
        title = "Murder Mystery 2",
        posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
    ),
    Movie(
        id = 638974,
        title = "Murder Mystery 2",
        posterPath = "/wdffZv8gIiWy6xr4t7hWBWtUwpl.jpg"
    ),
)


