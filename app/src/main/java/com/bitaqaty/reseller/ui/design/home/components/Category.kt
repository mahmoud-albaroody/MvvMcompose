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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.ui.design.home.Category
import com.bitaqaty.reseller.utils.TrapezoidShape

@Composable
fun CategoryItem(
    category: Category,
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
                    color = if(isSelected) Color.LightGray else Color(0xff0E46A3),
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
                contentDescription = "",
                tint = Color.Yellow
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 12.sp,
            color = if(isSelected) Color.LightGray else Color.Blue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    CategoryItem(
        category = Category("Featured", ""),
        isSelected = false
    )
}