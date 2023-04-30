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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jik.common.ui.component.PosterCard
import com.jik.core.model.Movie

@Composable
fun PopularScreen(
    modifier: Modifier = Modifier,
    popularViewModel: PopularViewModel = viewModel()
) {
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = popularViewModel.popularMock) { movie ->
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