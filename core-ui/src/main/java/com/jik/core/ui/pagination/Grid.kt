package com.jik.core.ui.pagination

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import com.jik.core.ui.pagination.Base.Operate
import com.jik.core.ui.pagination.Base.calculateShouldLoadMore


@Composable
fun LazyGridState.Pageable(
    onLoadMore: suspend () -> Unit,
    threshold: Int = 4
) {

    Operate(
        onLoadMore = onLoadMore,
        calculateShouldLoadMore = {
            calculateShouldLoadMore(
                layoutInfo = this.layoutInfo,
                threshold = threshold
            )
        }
    )
}