package com.jik.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.component.PosterCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    TransparentStatusBar()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Surface(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        HomeScreenTopContent(scrollBehavior = scrollBehavior)
    }
}

@Composable
fun TransparentStatusBar() {

    val systemUiController = rememberSystemUiController()
    val defaultColor = MaterialTheme.colorScheme.background

    DisposableEffect(Unit) {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
        onDispose {
            systemUiController.setStatusBarColor(color = defaultColor)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    MovieTopAppBar(
        titleRes = R.string.home,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopContent(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {

    val colorStops = arrayOf(
        0.0f to Color.White.copy(alpha = 0.5f),
        0.3f to Color.Transparent,
        0.7f to Color.Transparent,
        1.0f to Color.White,
    )


    PosterCard(
        posterPath = "https://image.tmdb.org/t/p/w500/8riWcADI1ekEiBguVB9vkilhiQm.jpg",
        modifier = modifier
            .aspectRatio(1f / 1.2f)
            .fillMaxSize(),
        roundedCornerSize = 0.dp,
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .aspectRatio(1f / 1.2f)
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = colorStops))
    )
    HomeScreenTopBar(scrollBehavior = scrollBehavior)
}