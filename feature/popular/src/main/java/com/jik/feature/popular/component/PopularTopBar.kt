package com.jik.feature.popular.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.feature.popular.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PopularTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    MovieTopAppBar(
        titleRes = R.string.popular,
        scrollBehavior = scrollBehavior
    )
}