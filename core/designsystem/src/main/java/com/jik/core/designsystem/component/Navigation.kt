package com.jik.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jik.core.designsystem.component.MovieNavigationBarItemDefaults.selectedIconColor
import com.jik.core.designsystem.component.MovieNavigationBarItemDefaults.selectedTextColor
import com.jik.core.designsystem.component.MovieNavigationBarItemDefaults.unselectedIconColor
import com.jik.core.designsystem.component.MovieNavigationBarItemDefaults.unselectedTextColor
import com.jik.core.designsystem.icon.MovieIcons


val NavigationBarCornerSize = 24.dp

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
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun RowScope.MovieNavigationBarItem(
    selected: Boolean,
    iconImageVector: ImageVector,
    selectedIconImageVector: ImageVector,
    modifier: Modifier = Modifier,
    labelText: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(targetValue = if (isPressed) 0.8f else 1f, label = "")

    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .weight(1f)
            .scale(scale.value)
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        MovieNavigationBarItemLabelAndIcon(
            selected = selected,
            iconImageVector = iconImageVector,
            selectedIconImageVector = selectedIconImageVector,
            labelText = labelText
        )
    }
}

@Composable
private fun MovieNavigationBarItemIcon(
    selected: Boolean,
    iconImageVector: ImageVector,
    selectedIconImageVector: ImageVector,
) {
    Icon(
        imageVector = if (selected) selectedIconImageVector else iconImageVector,
        tint = if (selected) selectedIconColor() else unselectedIconColor(),
        contentDescription = null
    )
}

@Composable
private fun MovieNavigationBarItemLabelAndIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
    iconImageVector: ImageVector,
    selectedIconImageVector: ImageVector,
    labelText: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieNavigationBarItemIcon(
            selected = selected,
            iconImageVector = iconImageVector,
            selectedIconImageVector = selectedIconImageVector
        )
        Text(
            text = labelText,
            color = if (selected) selectedTextColor() else unselectedTextColor(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun FakeNavigationBarItemLabelAndIcon(
    modifier: Modifier = Modifier,
) {
    MovieNavigationBarItemLabelAndIcon(
        modifier = modifier,
        selected = false,
        iconImageVector = MovieIcons.HomeRounded,
        selectedIconImageVector = MovieIcons.HomeRounded,
        labelText = ""
    )
}

private object MovieNavigationBarItemDefaults {
    @Composable
    fun selectedIconColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun selectedTextColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun unselectedIconColor() = MaterialTheme.colorScheme.outline

    @Composable
    fun unselectedTextColor() = MaterialTheme.colorScheme.outline
}