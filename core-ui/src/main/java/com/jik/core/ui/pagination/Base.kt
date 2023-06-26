package com.jik.core.ui.pagination

import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.grid.LazyGridLayoutInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember


internal object Base {

    @Composable
    fun Operate(calculateShouldLoadMore: () -> Boolean, onLoadMore: suspend () -> Unit) {
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

    // means that if the last visible item is 'threshold' or less from the end of the list, return true
    fun <T> calculateShouldLoadMore(layoutInfo: T, threshold: Int): Boolean {
        val itemCount = when (layoutInfo) {
            is LazyGridLayoutInfo -> layoutInfo.totalItemsCount
            is LazyListLayoutInfo -> layoutInfo.totalItemsCount
            else -> throw IllegalArgumentException("Unsupported layoutInfo type")
        }
        val lastVisibleItem = when (layoutInfo) {
            is LazyGridLayoutInfo -> layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            is LazyListLayoutInfo -> layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            else -> throw IllegalArgumentException("Unsupported layoutInfo type")
        }

        return lastVisibleItem >= itemCount - threshold
    }
}