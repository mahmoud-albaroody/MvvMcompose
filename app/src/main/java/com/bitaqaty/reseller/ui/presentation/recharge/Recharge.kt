package com.bitaqaty.reseller.ui.presentation.recharge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.profileScreen.ProfileFooter
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey200


@Composable
fun RechargeScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: RechargeViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    Recharge()
}


@Preview
@Composable
fun Recharge() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DynamicSelectTextField()
        RechargeAmount()
        Column(Modifier.padding(top = Dimens.DefaultMargin)) {
            ProfileFooter("Maximum recharge amount is 5000 SAR", R.drawable.ic_info_circle)
            ProfileFooter("Minimum recharge amount is 2 SAR", R.drawable.ic_info_circle)
            ProfileFooter(
                "Max number of recharge per day 10 transactions",
                R.drawable.ic_info_circle
            )
        }
        Box(Modifier.padding(top = Dimens.DefaultMargin20)) {
            FilterButton(
                backgroundTex = BebeBlue, text = "Recharge",
                iconVisibility = false, textColor = Color.White,
                onApplyFilterClick = {

                }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding30)
                .padding(top = Dimens.halfDefaultMargin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "By continue recharging you agree on",
                style = TextStyle(color = LightGrey200, fontSize = 11.sp)
            )
            Text(
                text = "Terms and Conditions",
                style = TextStyle(color = BebeBlue, fontSize = 11.sp)
            )
        }
    }
}

@Preview
@Composable
fun RechargeAmount() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)

    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                )
                .padding(
                    top = Dimens.DefaultMargin20,
                    bottom = Dimens.quarterDefaultMargin
                ),
            text = "Recharge Amount",
            style = TextStyle(color = LightGrey200, fontSize = 14.sp)
        )
        Box(
            modifier = Modifier.padding(
                bottom = Dimens.padding30
            )
        ) {
            DynamicSelectTextField()
        }
    }
}

