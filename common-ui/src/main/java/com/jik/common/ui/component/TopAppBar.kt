package com.jik.common.ui.component

import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jik.common.ui.preview.ThemePreviews
import com.jik.common.ui.theme.MovieTheme

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