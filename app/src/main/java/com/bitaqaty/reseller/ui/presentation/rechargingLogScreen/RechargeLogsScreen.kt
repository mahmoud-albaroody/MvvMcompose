package com.bitaqaty.reseller.ui.presentation.rechargingLogScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationHeader
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationItem
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey300

@Composable
fun RechargeLogScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: RechargeLogViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    RechargeLog(onFilterClick = {
        navController.navigate(Screen.ApplyFilterScreen.route)
    })
}

//
//@Preview
@Composable
fun RechargeLog(onFilterClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        RechargeLogHeader(
            LightGrey300,
            "Date/Time",
            "Method",
            "Amount",
            "Balance After",
            FontWeight.Bold,
            textAlign = TextAlign.Center,
            true
        )

        Box(Modifier.height(screenHeight * 0.50f)) {
            RechargeLogItems(true)
        }

        Filter(onFilterClick = {
            onFilterClick.invoke()
        })

    }


}



@Composable
fun RechargeLogItems(isViewMothed: Boolean) {
    LazyColumn(
        Modifier
            .fillMaxSize(), content = {
            items(10) {
                RechargeLogHeader(
                    LightGrey100,
                    "12/12/2023 03:11:55 PM",
                    "Mada Alhly",
                    "500",
                    "100",
                    null,
                    textAlign = TextAlign.Start,
                    isViewMothed
                )
            }
        })
}


@Composable
fun RechargeLogHeader(
    cardColor: Color, date: String, method: String,
    amount: String, balance: String,
    fontWeight: FontWeight?, textAlign: TextAlign,
    isViewMothed: Boolean
) {
    Card(
        Modifier
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        elevation = CardDefaults.cardElevation(Dimens.DefaultMargin1),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {

        ChargeItemText(
            date,
            method,
            amount,
            balance,
            fontWeight,
            textAlign,
            isViewMothed
        )
    }
}


@Composable
private fun ChargeItemText(
    date: String, method: String,
    amount: String, balance: String,
    fontWeight: FontWeight?,
    textAlign: TextAlign, isViewMothed: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.DefaultMargin, horizontal = Dimens.halfDefaultMargin),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = TextStyle(
                textAlign = textAlign,
                color = FontColor,
                fontSize = 12.sp,
                fontWeight = fontWeight
            ),
            text = date
        )
        if (isViewMothed)
            Text(
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = FontColor,
                    fontSize = 12.sp,
                    fontWeight = fontWeight
                ),
                text = method
            )
        Text(
            modifier = Modifier.weight(1f),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = FontColor,
                fontSize = 12.sp,
                fontWeight = fontWeight
            ),
            text = amount
        )
        Text(
            modifier = Modifier.weight(1f),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = FontColor, fontWeight = fontWeight
            ),
            text = balance
        )
    }
}





