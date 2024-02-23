package com.jik.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.icon.MovieIcons


@Composable
fun Refresh(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    size: Dp = 40.dp,
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = MovieIcons.Refresh,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Refresh",
            modifier = Modifier.size(size)
        )
    }
}