package com.jik.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingWheel(
    modifier: Modifier = Modifier,
    circleSize: Dp = 40.dp,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    contentPadding: PaddingValues = PaddingValues()
) {

    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = strokeWidth,
        modifier = modifier
            .size(circleSize)
            .padding(contentPadding)
    )
}


@Preview
@Composable
fun PreviewLoadingWheel() {
    Box(modifier = Modifier.fillMaxSize()) {
        LoadingWheel(
            modifier = Modifier.align(Alignment.Center),
            circleSize = 40.dp,
            strokeWidth = 4.dp
        )
    }
}