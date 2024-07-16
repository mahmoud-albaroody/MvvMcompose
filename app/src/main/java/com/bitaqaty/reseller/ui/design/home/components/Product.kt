package com.bitaqaty.reseller.ui.design.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utils.noRippleClickable

@Composable
fun Product(
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(Dimens.padding12)
            .wrapContentWidth()
            .noRippleClickable { onClick() },
        shape = RoundedCornerShape(
            topEndPercent = Dimens.cornerRadius20,
            bottomStartPercent = Dimens.cornerRadius15,
            bottomEndPercent = Dimens.cornerRadius15
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier
            .wrapContentWidth()
            .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UpperProductSection()
            LowerProductSection()
        }
    }
}

@Composable
fun UpperProductSection() {
    Card(
        modifier = Modifier.wrapContentWidth(),
        shape = RoundedCornerShape(
            topEndPercent = 20,
            bottomStartPercent = 20,
            bottomEndPercent = 20
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.Gray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                textAlign = TextAlign.Center,
                text = "ITunes",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )

            Row(
                modifier = Modifier.wrapContentWidth().align(Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    textAlign = TextAlign.Start,
                    text = "$",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.White,
                            offset = Offset(10f, 10f),
                            blurRadius = 30f
                        )
                    )
                )
                Text(
                    modifier = Modifier.padding(end = 4.dp).wrapContentWidth(),
                    text = "10",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = Color.Black,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.LightGray,
                            offset = Offset(60f, 80f),
                            blurRadius = 70f
                        ),
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
            }
            Row(
                modifier = Modifier.wrapContentWidth().align(Alignment.End),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(36.dp),
                    painter = painterResource(id = R.drawable.flag),
                    contentDescription = "Image"
                )
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .wrapContentWidth()
                        .padding(end = 4.dp)
                        .size(80.dp),
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "Image",
                    alignment = Alignment.TopEnd
                )
            }
        }
    }
}

@Composable
fun LowerProductSection() {
    Column(
        modifier = Modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
            text = "18.75",
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally).padding(bottom = 8.dp),
            text = "SAR",
            fontSize = 12.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductPreview() {
    Product(onClick = {})
}