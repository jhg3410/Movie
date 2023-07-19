package com.jik.core.ui.util.modifier

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {

    var lastClickTime by remember { mutableStateOf(0L) }

    this.clickable(enabled = enabled) {
        if (System.currentTimeMillis() - lastClickTime > 1000L) {
            lastClickTime = System.currentTimeMillis()
            onClick()
        }
    }
}
