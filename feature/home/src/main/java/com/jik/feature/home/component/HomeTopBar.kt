package com.jik.feature.home.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.theme.MovieFontFamily
import com.jik.core.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTopBar(
    modifier: Modifier = Modifier,
) {
    MovieTopAppBar(
        titleRes = R.string.logo,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.0f
            )
        ),
        titleStyle = MaterialTheme.typography.displayMedium,
        titleFontFamily = MovieFontFamily.LilitaOne,
    )
}