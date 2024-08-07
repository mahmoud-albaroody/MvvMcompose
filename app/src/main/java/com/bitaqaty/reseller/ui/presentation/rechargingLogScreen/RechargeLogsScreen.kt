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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.RechargingLog
import com.bitaqaty.reseller.data.model.SettlementLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationHeader
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationItem
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey300
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils

@Composable
fun RechargeLogScreen(navController: NavController, modifier: Modifier) {
    val rechargeLogViewModel: RechargeLogViewModel = hiltViewModel()
    val discrmenationVal = ""
    val dateFrom = ""
    val dateTo = ""
    val pageIndex = 1
    val dateMethod: String? = null

    LaunchedEffect(key1 = true) {
        rechargeLogViewModel.getRechargingList(
            pageIndex,
            discrmenationVal,
            dateFrom,
            dateTo,
            dateMethod
        )
    }
    rechargeLogViewModel.rechargeLogs.value.let {
        RechargeLog(it?.resultList, onFilterClick = {
            navController.navigate(Screen.ApplyFilterScreen.route.plus(
                Screen.ApplyFilterScreen.objectName
                        + "rechargeLog"
            ))
        })
    }

}

//
//@Preview
@Composable
fun RechargeLog(rechargingLogs: ArrayList<RechargingLog>?, onFilterClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.height(screenHeight * 0.70f)) {
            Column {
                RechargeLogHeader(
                    LightGrey300,
                    stringResource(id = R.string.date_time),
                    stringResource(id = R.string.method),
                    stringResource(id = R.string.amount),
                    stringResource(id = R.string.balance_after),
                    FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    true
                )
                RechargeLogItems(true, rechargingLogs = rechargingLogs)
            }
        }
        Column(Modifier.height(screenHeight * 0.15f)) {
            Filter(
                onFilterClick = {
                    onFilterClick.invoke()
                })
        }

    }


}


@Composable
fun RechargeLogItems(
    isViewMothed: Boolean,
    settlementLog: ArrayList<SettlementLog>? = null,
    rechargingLogs: ArrayList<RechargingLog>? = null
) {
    LazyColumn(
        Modifier
            .fillMaxSize(), content = {
            if (settlementLog != null) {
                items(settlementLog) {
                    val amount = "${it.amount} ${Utils.getUserCurrency()}"
                    val status: String = when (it.status) {
                        "PENDING" -> {
                            stringResource(R.string.pending)
                        }

                        "DONE" ->
                            stringResource(R.string.done1)

                        else -> {
                            stringResource(R.string.rejected)
                        }
                    }
                    RechargeLogHeader(
                        LightGrey100,
                        it.creationDate,
                        stringResource(id = R.string.mada_ahly),
                        amount,
                        status,
                        null,
                        textAlign = TextAlign.Center,
                        isViewMothed
                    )
                }
            } else if (rechargingLogs != null) {
                items(rechargingLogs) {
                    val amount = "${it.amount} ${Utils.getUserCurrency()}"
                    RechargeLogHeader(
                        LightGrey100,
                        it.getCheckingDate(),
                        it.getChargingMethod(),
                        amount,
                        it.getBalanceAfterValue().toString(),
                        null,
                        textAlign = TextAlign.Center,
                        isViewMothed
                    )
                }
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
                vertical = Dimens.padding4
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
            .padding(vertical = Dimens.DefaultMargin,
                horizontal = Dimens.DefaultMargin),
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





