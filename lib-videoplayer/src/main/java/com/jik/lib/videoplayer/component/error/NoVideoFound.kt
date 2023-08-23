package com.jik.lib.videoplayer.component.error

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun NoVideoFound() {
    Text(
        text = "No video found.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White,
    )
}