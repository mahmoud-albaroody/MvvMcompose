package com.bitaqaty.reseller.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

val Shapes = androidx.compose.material3.Shapes(
    extraLarge = RoundedCornerShape(28.dp),
    large = RoundedCornerShape(24.dp),
    medium = RoundedCornerShape(16.dp),
    small = RoundedCornerShape(12.dp),
    extraSmall = RoundedCornerShape(8.dp)
)

fun Modifier.cornerRadius(radius: Int) =
    graphicsLayer(shape = RoundedCornerShape(radius.dp), clip = true)