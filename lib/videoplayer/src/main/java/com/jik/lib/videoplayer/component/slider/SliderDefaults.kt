package com.jik.lib.videoplayer.component.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal object MovieSliderDefaults {

    @Composable
    fun Thumb(
        modifier: Modifier = Modifier,
        color: Color,
        shape: Shape = CircleShape,
        interactionSource: MutableInteractionSource,
    ) {
        Spacer(
            modifier = modifier
                .padding(top = 2.dp)
                .size(size = 12.dp)
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(
                        bounded = false,
                        radius = 18.dp
                    )
                )
                .hoverable(interactionSource = interactionSource)
                .clip(shape = shape)
                .background(color = color)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Track(
        modifier: Modifier = Modifier,
        state: SliderState,
        activeColor: Color,
        inactiveColor: Color,
        height: Dp = 2.dp,
    ) {
        val coercedValueAsFraction = calcFraction(
            state.valueRange.start,
            state.valueRange.endInclusive,
            state.value
        )

        Canvas(
            modifier
                .fillMaxWidth()
                .height(height)
        ) {
            val isRtl = layoutDirection == LayoutDirection.Rtl
            val sliderLeft = Offset(0f, center.y)
            val sliderRight = Offset(size.width, center.y)
            val sliderStart = if (isRtl) sliderRight else sliderLeft
            val sliderEnd = if (isRtl) sliderLeft else sliderRight
            val trackStrokeWidth = height.toPx()

            val sliderValueStart = Offset(
                x = sliderStart.x,
                y = center.y
            )

            val sliderValueEnd = Offset(
                x = sliderEnd.x * coercedValueAsFraction,
                y = center.y
            )

            // inactive track
            drawLine(
                color = inactiveColor,
                start = sliderStart,
                end = sliderEnd,
                strokeWidth = trackStrokeWidth,
                cap = StrokeCap.Round
            )

            // active track
            drawLine(
                color = activeColor,
                start = sliderValueStart,
                end = sliderValueEnd,
                strokeWidth = trackStrokeWidth,
                cap = StrokeCap.Round
            )
        }
    }

    @Composable
    fun colors(
        thumbColor: Color = Color.Unspecified,
        activeTrackColor: Color = Color.Unspecified,
        inactiveTrackColor: Color = Color.Unspecified,
    ): MovieSliderColors = MovieSliderColors(
        thumbColor = thumbColor,
        activeTrackColor = activeTrackColor,
        inactiveTrackColor = inactiveTrackColor,
    )
}

private fun calcFraction(start: Float, end: Float, currentValue: Float): Float =
    if (end == start) 0f
    else ((currentValue - start) / (end - start)).coerceIn(0f, 1f)


internal class MovieSliderColors(
    val thumbColor: Color,
    val activeTrackColor: Color,
    val inactiveTrackColor: Color,
)