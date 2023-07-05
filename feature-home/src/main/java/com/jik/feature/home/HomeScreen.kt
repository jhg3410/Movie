package com.jik.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jik.core.designsystem.component.NavigationBarCornerSize

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = NavigationBarCornerSize),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.labelLarge
        )
    }
}