package com.jik.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp


@Composable
fun MovieNavigationBar(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        topStart = NavigationBarCornerSize,
        topEnd = NavigationBarCornerSize
    ),
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .height(58.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun RowScope.MovieNavigationBarItem(
    selected: Boolean,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
) {
    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        if (label == null) {
            NavigationItemIcon(
                selected = selected,
                icon = icon,
                selectedIcon = selectedIcon
            )
        } else {
            NavigationItemLabelAndIcon(
                selected = selected,
                icon = icon,
                selectedIcon = selectedIcon,
                label = label
            )
        }
    }
}

@Composable
private fun NavigationItemLabelAndIcon(
    selected: Boolean,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationItemIcon(
            selected = selected,
            icon = icon,
            selectedIcon = selectedIcon
        )
        label()
    }
}

@Composable
private fun NavigationItemIcon(
    selected: Boolean,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit
) {
    if (selected) {
        selectedIcon()
    } else {
        icon()
    }
}

val NavigationBarCornerSize = 24.dp