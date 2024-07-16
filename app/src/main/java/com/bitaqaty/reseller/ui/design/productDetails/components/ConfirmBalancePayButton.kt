package com.bitaqaty.reseller.ui.design.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utils.NoRippleInteractionSource

@Composable
fun ConfirmBalancePayButton(
    onClick: () -> Unit
){
    Button(
        modifier = Modifier
            .background(Color(0xFF3155A4), shape = RoundedCornerShape(Dimens.cornerRadius15))
            .fillMaxHeight()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        contentPadding = PaddingValues(),
        interactionSource = NoRippleInteractionSource(),
        onClick = onClick,
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
                Column() {
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

@Preview(showBackground = false)
@Composable
fun ConfirmBalancePayButtonPreview() {
    ConfirmBalancePayButton(
        onClick = {}
    )
}