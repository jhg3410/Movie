package com.jik.core.ui.pagination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

internal object BasePageable {

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
    fun calculateShouldLoadMore(itemCount: Int, lastVisibleItem: Int, threshold: Int): Boolean {
        return lastVisibleItem >= itemCount - threshold
    }
}