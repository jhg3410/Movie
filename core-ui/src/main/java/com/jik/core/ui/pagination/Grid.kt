package com.jik.core.ui.pagination

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember


@Composable
fun LazyGridState.Pageable(
    onLoadMore: suspend () -> Unit,
    threshold: Int = 5
) {
    fun calculateShouldLoadMore(): Boolean {
        val itemCount = layoutInfo.totalItemsCount
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

        return lastVisibleItem >= itemCount - threshold
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            calculateShouldLoadMore()
        }
    }

    LaunchedEffect(key1 = shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }
}
