package com.bitaqaty.reseller.ui.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Typography.Label: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = frutigerLTArabic,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp,
        )
    }

val Typography.PlaceHolder: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = frutigerLTArabic,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
        )
    }