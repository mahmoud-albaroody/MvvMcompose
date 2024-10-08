package com.bitaqaty.reseller.ui.presentation.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsViewModel
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.NoRippleInteractionSource
import kotlin.math.abs

@Composable
fun ConfirmBalancePayButton(
    viewModel: ProductDetailsViewModel,
    onSwipe: () -> Unit
){
    var offsetX by remember { mutableFloatStateOf(0f) }
    val maxOffsetX = 180f
    val layoutDirection = LocalLayoutDirection.current

    Button(
        modifier = Modifier
            .fillMaxHeight()
            .offset(x = offsetX.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {viewModel.toggleOpacity(0.5f)},
                    onDragEnd = {
                        if (abs(offsetX) < maxOffsetX) {
                            offsetX = 0f
                            viewModel.toggleOpacity(1f)
                        }else{
                            onSwipe()
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX = when (layoutDirection) {
                        LayoutDirection.Ltr -> (offsetX + dragAmount.x).coerceIn(0f, maxOffsetX)
                        LayoutDirection.Rtl -> (offsetX - dragAmount.x).coerceIn(0f, maxOffsetX)
                    }
                    if(offsetX == maxOffsetX) viewModel.toggleOpacity(0f)
                    if(offsetX < maxOffsetX) viewModel.toggleOpacity(0.5f)
                }
            }
            .zIndex(if (offsetX > 0f) 1f else 0f)
            .background(Color(0xFF3155A4), shape = RoundedCornerShape(Dimens.cornerRadius15))
            .padding(horizontal = 18.dp, vertical = 8.dp),
        contentPadding = PaddingValues(),
        interactionSource = NoRippleInteractionSource(),
        onClick = {},
        shape = RoundedCornerShape(Dimens.cornerRadius15),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3155A4)),
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .height(28.dp)
                        .width(36.dp)
                        .padding(end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_wallet),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    Text(
                        text = "Slide to",
                        style = TextStyle(
                            fontFamily = frutigerLTArabic,
                            fontSize = 12.sp,
                            lineHeight = TextUnit.Unspecified
                        ),
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    )
                    Text(
                        text = "Confirm",
                        style = TextStyle(
                            fontFamily = frutigerLTArabic,
                            fontSize = 16.sp,
                            lineHeight = TextUnit.Unspecified
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.Bottom),
                    painter = painterResource(id = R.drawable.ic_narrow_arrow),
                    tint = Color.White,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    painter = painterResource(id = R.drawable.ic_medium_arrow),
                    tint = Color.White,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    painter = painterResource(id = R.drawable.ic_thick_arrow),
                    tint = Color.White,
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 2.dp),
                    text = "Balance",
                    style = TextStyle(
                        fontFamily = frutigerLTArabic,
                        fontSize = 12.sp,
                        lineHeight = TextUnit.Unspecified
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xC3FFFFFF),
                )
                Text(
                    modifier = Modifier
                        .padding(end = 2.dp),
                    text = "15000,00",
                    style = TextStyle(
                        fontFamily = frutigerLTArabic,
                        fontSize = 12.sp,
                        lineHeight = TextUnit.Unspecified
                    ),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "SAR",
                    style = TextStyle(
                        fontFamily = frutigerLTArabic,
                        fontSize = 12.sp,
                        lineHeight = TextUnit.Unspecified
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xCBBABABA),
                )
            }
        }
    }
}

//@Preview(showBackground = false)
//@Composable
//fun ConfirmBalancePayButtonPreview() {
//    ConfirmBalancePayButton(
//        onClick = {}
//    )
//}