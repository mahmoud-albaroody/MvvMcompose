package com.bitaqaty.reseller.ui.presentation.rechargingLogScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.RechargingLog
import com.bitaqaty.reseller.data.model.SettlementLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey300
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun RechargeLogScreen(navController: NavController, modifier: Modifier, obj: JSONObject?) {
    val rechargeLogViewModel: RechargeLogViewModel = hiltViewModel()
    val rechargingLogsList = remember { mutableStateListOf<RechargingLog>() }
    var discrmenationVal = ""
    var pageIndex = 1
    var accountNo: Int? = Utils.getUserData()?.reseller?.id
    var channel: String? = null
    var paymentMethod: String? = null
    var selectedDateTo: String = ""
    var selectedDateFrom: String = ""
    var totalElementsCount by remember { mutableIntStateOf(0) }


    var searchPeriod: String? = Globals.DATE.CURRENT_MONTH.value
    obj?.let {

        if (obj.getInt("accountNo") != 0) {
            accountNo = obj.get("accountNo") as Int?
        }

        if (!obj.getString("searchPeriod").isNullOrEmpty()) {
            searchPeriod = obj.getString("searchPeriod")
        }
        if (!obj.getString("paymentMethod").isNullOrEmpty()) {
            paymentMethod = obj.getString("paymentMethod")
        }

        if (!obj.getString("selectedDateTo").isNullOrEmpty()) {
            selectedDateTo = obj.getString("selectedDateTo")
        }
        if (!obj.getString("selectedDateFrom").isNullOrEmpty()) {
            selectedDateFrom = obj.getString("selectedDateFrom")
        }
        if (!obj.getString("discrmenationVal").isNullOrEmpty()) {
            discrmenationVal = obj.getString("discrmenationVal")
        }


    }

    LaunchedEffect(key1 = true) {
        rechargeLogViewModel.getRechargingList(
            pageIndex,
            discrmenationVal,
            selectedDateFrom,
            selectedDateTo,
            searchPeriod
        )
        rechargeLogViewModel.viewModelScope.launch {
            rechargeLogViewModel.rechargeLogs.collect {
                it.data?.resultList?.let { it1 -> rechargingLogsList.addAll(it1) }
                totalElementsCount = it.data?.totalElementsCount ?: 0
            }
        }
    }

    RechargeLog(rechargingLogsList, totalElementsCount = totalElementsCount, onClick = {
        rechargeLogViewModel.getRechargingList(
            ++pageIndex,
            discrmenationVal,
            selectedDateFrom,
            selectedDateTo,
            searchPeriod
        )
    }) {
        navController.navigate(
            Screen.ApplyFilterScreen.route.plus(
                Screen.ApplyFilterScreen.objectName
                        + "rechargeLog"
            )
        )
    }
}

//
//@Preview
@Composable
fun RechargeLog(
    rechargingLogs: SnapshotStateList<RechargingLog>,
    totalElementsCount: Int,
    onClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.weight(1f)) {
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
                RechargeLogItems(true, totalElementsCount = totalElementsCount, onClick = {
                    onClick()
                }, rechargingLogs = rechargingLogs)
            }
        }
        Filter(
            onFilterClick = {
                onFilterClick.invoke()
            })


    }


}


@Composable
fun RechargeLogItems(
    isViewMothed: Boolean,
    onClick: () -> Unit,
    totalElementsCount: Int,
    settlementLog: SnapshotStateList<SettlementLog>? = null,
    rechargingLogs: SnapshotStateList<RechargingLog>? = null
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

            if (settlementLog == null) {
                if (totalElementsCount > rechargingLogs?.size!!)
                    item {
                        if (rechargingLogs.isNotEmpty())
                            onClick()
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(14.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    }
            } else {

                if (totalElementsCount > settlementLog.size)
                    item {
                        if (settlementLog.isNotEmpty())
                            onClick()
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(14.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
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
            .padding(
                vertical = Dimens.DefaultMargin,
                horizontal = Dimens.DefaultMargin
            ),
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





