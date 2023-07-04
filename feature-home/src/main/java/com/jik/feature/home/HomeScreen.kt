package com.jik.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.labelLarge
        )
    }
}