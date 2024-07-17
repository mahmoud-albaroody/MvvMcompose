package com.bitaqaty.reseller.ui.presentation.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utils.NoRippleInteractionSource

@Composable
fun DoneButton(
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        interactionSource = NoRippleInteractionSource(),
        shape = RoundedCornerShape(Dimens.cornerRadius15),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF23A212)),
        contentPadding = PaddingValues(),
        modifier = Modifier
            .height(80.dp)
            .background(Color(0xFF23A212), shape = RoundedCornerShape(Dimens.cornerRadius15))
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_check_circle),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .padding(end = 4.dp, top = 2.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                fontFamily = frutigerLTArabic,
                text = "Done",
                fontSize = 28.sp,
                color = Color.White,
                lineHeight = TextUnit.Unspecified,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DoneButtonPreview() {
    DoneButton(
        onClick = {}
    )
}