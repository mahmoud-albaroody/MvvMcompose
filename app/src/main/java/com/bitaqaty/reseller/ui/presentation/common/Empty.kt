package com.bitaqaty.reseller.ui.presentation.common


import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bitaqaty.reseller.R

@Composable
fun Empty() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        modifier = Modifier
            .size(350.dp),
            //.padding(16.dp),
        composition = composition,
        progress =  progress ,
        alignment = Alignment.Center
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    Empty()
}