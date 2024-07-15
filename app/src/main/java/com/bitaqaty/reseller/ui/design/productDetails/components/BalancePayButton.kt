package com.bitaqaty.reseller.ui.design.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BalancePayButton(
    onClick: () -> Unit
){
    Button(
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(Dimens.cornerRadius15))
            .padding(horizontal = 24.dp, vertical = 6.dp),
        contentPadding = PaddingValues(),
        interactionSource = NoRippleInteractionSource(),
        onClick = onClick,
        shape = RoundedCornerShape(Dimens.cornerRadius15),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            ) {
                Image(
                    modifier = Modifier
                        .height(36.dp)
                        .width(52.dp)
                        .padding(end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_wallet),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    Text(
                        text = "Balance",
                        style = TextStyle(
                            fontFamily = frutigerLTArabic,
                            fontSize = 12.sp,
                            lineHeight = TextUnit.Unspecified
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF9D9D9D),
                    )
                    Text(
                        text = "15000,00",
                        style = TextStyle(
                            fontFamily = frutigerLTArabic,
                            fontSize = 12.sp,
                            lineHeight = TextUnit.Unspecified
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF717171),
                    )
                    Text(
                        text = "SAR",
                        style = TextStyle(
                            fontFamily = frutigerLTArabic,
                            fontSize = 12.sp,
                            lineHeight = TextUnit.Unspecified
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 2.dp),
                style = TextStyle(
                    fontFamily = frutigerLTArabic,
                    fontSize = 16.sp,
                    lineHeight = TextUnit.Unspecified
                ),
                text = "Pay With Wallet",
                color = Color(0xFF1C274C)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun BalancePayButtonPreview() {
    BalancePayButton(
        onClick = {}
    )
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true

}