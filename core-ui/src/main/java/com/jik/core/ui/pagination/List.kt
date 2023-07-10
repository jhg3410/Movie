package com.jik.core.ui.pagination

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.jik.core.ui.pagination.Base.Operate
import com.jik.core.ui.pagination.Base.calculateShouldLoadMore

@Composable
fun LazyListState.Pageable(
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