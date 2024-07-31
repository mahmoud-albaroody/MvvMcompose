package com.bitaqaty.reseller.ui.presentation.settlementTransaction


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.SettlementLog
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogHeader
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogItems
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey300
import com.bitaqaty.reseller.ui.theme.White

@Composable
fun SettlementTransactionsScreen(navController: NavController, modifier: Modifier) {
    val settlementTransactionsViewModel: SettlementTransactionsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        settlementTransactionsViewModel.settlementLog(1, 1)
    }
    settlementTransactionsViewModel.settlementLogs.value.let {
        SettlementTransactions(it?.requestsLogs)
    }

}

@Composable
fun SettlementTransactions(settlementLog: ArrayList<SettlementLog>?) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {

        FilterButton(
            backgroundTex = Blue100, text = stringResource(id = R.string.transfer_request),
            iconVisibility = true, horizontalPadding = Dimens.DefaultMargin, textColor = White
        ) {

        }
        RechargeLogHeader(
            LightGrey300,
            stringResource(id = R.string.date_time),
            stringResource(id = R.string.method),
            stringResource(id = R.string.amount),
            stringResource(id = R.string.status),
            FontWeight.Bold,
            textAlign = TextAlign.Center, false
        )

        Box(Modifier.height(screenHeight * 0.68f)) {
            RechargeLogItems(false, settlementLog)
        }

    }
}