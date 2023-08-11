package com.jik.core.ui.pagination

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable

@Composable
fun LazyGridState.Pageable(
    onLoadMore: suspend () -> Unit,
    threshold: Int = 4
) {

    fun calculateShouldLoadMore() =
        BasePageable.calculateShouldLoadMore(
            itemCount = layoutInfo.totalItemsCount,
            lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0,
            threshold = threshold
        )

    BasePageable.Operate(
        calculateShouldLoadMore = { calculateShouldLoadMore() },
        onLoadMore = onLoadMore
    )
}