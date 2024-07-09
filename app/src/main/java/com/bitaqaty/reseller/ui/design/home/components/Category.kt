package com.bitaqaty.reseller.ui.design.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.ui.design.home.TrapezoidShape

@Composable
fun Category(
    contentDescription: String,
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundModifier = if (isSelected) {
        modifier.background(color = Color.White, shape = TrapezoidShape())
    } else {
        modifier
    }

    Column(
        modifier = backgroundModifier.padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(45.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawArc(
                    color = Color.Gray,
                    startAngle = 320f,
                    sweepAngle = 340f,
                    useCenter = false,
                    style = Stroke(width = 6f)
                )
            }
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .padding(2.dp),
                imageVector = Icons.Default.Star,
                contentDescription = contentDescription,
                tint = Color.Yellow
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 16.sp, color = Color.Blue)
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    Category(
        contentDescription = "Star Icon",
        name = "Featured",
        isSelected = true
    )
}