package com.jik.core.ui.util.modifier

import androidx.compose.ui.Modifier


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier =
    if (condition) this.modifier() else this