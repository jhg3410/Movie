package com.jik.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.preview.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.background
    ),
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun TopAppBarPreview() {
    MovieTheme(
        dynamicColor = false
    ) {
        MovieTopAppBar(
            titleRes = android.R.string.untitled,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}

// TopAppBarSmallTokens.ContainerHeight
object MovieTopAppBar {
    val height = 64.dp
}
