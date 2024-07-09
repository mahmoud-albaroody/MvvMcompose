package com.bitaqaty.reseller.ui.design.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class TrapezoidShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, size.height - size.height * 0.15f)
            lineTo(size.width, size.height)
            lineTo(size.width, 0f)
            lineTo(0f, size.height * 0.15f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun TrapezoidBox(size: Dp = 100.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(color = Color.White, shape = TrapezoidShape())
    )
}

@Preview
@Composable
fun PreviewTrapezoidShape() {
    TrapezoidBox()
}