package com.jik.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.jik.core.designsystem.icon.MovieIcons.ArrowBackRounded
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.preview.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    @StringRes titleRes: Int?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.background
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleFontFamily: FontFamily? = null,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    titleWeight: FontWeight = FontWeight.Bold,
    canNavigateBack: Boolean = false,
    navigateBack: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (titleRes != null) {
                Text(
                    text = stringResource(id = titleRes),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = titleFontFamily,
                    fontWeight = titleWeight,
                    style = titleStyle
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = ArrowBackRounded,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun TopAppBarPreview() {
    MovieTheme {
        MovieTopAppBar(
            titleRes = android.R.string.untitled,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}