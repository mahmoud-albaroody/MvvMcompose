package com.bitaqaty.reseller.ui.presentation.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utils.NoRippleInteractionSource

@Composable
fun MadaPayButton(
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        interactionSource = NoRippleInteractionSource(),
        shape = RoundedCornerShape(Dimens.cornerRadius15),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(),
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(Dimens.cornerRadius15))
            .border(color = Color(0xFF3155A5), width = 2.dp, shape = RoundedCornerShape(Dimens.cornerRadius15))
            .padding(horizontal = 28.dp, vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_mada_pay),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(32.dp)
                    .width(96.dp)
                    .padding(top = 4.dp)
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = frutigerLTArabic,
                text = "Pay With Card",
                fontSize = 16.sp,
                color = Color(0xFF1C274C)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MadaPayButtonPreview() {
    MadaPayButton(
        onClick = {}
    )
}