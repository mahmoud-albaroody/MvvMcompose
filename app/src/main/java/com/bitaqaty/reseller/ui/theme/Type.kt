package com.bitaqaty.reseller.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = urLTArabic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

)


val subTitleSecondary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = SecondaryFontColor,
            fontSize = 10.sp,
        )
    }

val bioGrapyText: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = SecondaryFontColor,
            fontSize = 14.sp,
        )
    }

val subTitlePrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = FontColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }

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
            fontSize = 16.sp,
        )
    }

val Typography.Counter: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = frutigerLTArabic,
            fontWeight = FontWeight.Light,
            fontSize = 28.sp,
        )
    }