package com.jik.lib.videoplayer.component.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

object MovieSliderDefaults {

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
}

private fun calcFraction(start: Float, end: Float, currentValue: Float): Float =
    if (end == start) 0f
    else ((currentValue - start) / (end - start)).coerceIn(0f, 1f)

