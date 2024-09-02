package com.jik.feature.detail.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jik.core.designsystem.component.MovieTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    MovieTopAppBar(
        titleRes = null,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        canNavigateBack = true,
        navigateBack = navigateUp
    )
}