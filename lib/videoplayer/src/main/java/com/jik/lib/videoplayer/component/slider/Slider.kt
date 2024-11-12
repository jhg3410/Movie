package com.jik.lib.videoplayer.component.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jik.lib.videoplayer.component.slider.MovieSliderDefaults.Thumb
import com.jik.lib.videoplayer.component.slider.MovieSliderDefaults.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieSlider(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit = {},
    enabled: Boolean = true,
    colors: MovieSliderColors = MovieSliderDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        modifier = modifier,
        value = value,
        valueRange = valueRange,
        onValueChange = onValueChange,
        enabled = enabled,
        interactionSource = interactionSource,
        thumb = {
            Thumb(
                interactionSource = interactionSource,
                color = colors.thumbColor
            )
        },
        track = { state ->
            Track(
                state = state,
                activeColor = colors.activeTrackColor,
                inactiveColor = colors.inactiveTrackColor
            )
        }
    )
}