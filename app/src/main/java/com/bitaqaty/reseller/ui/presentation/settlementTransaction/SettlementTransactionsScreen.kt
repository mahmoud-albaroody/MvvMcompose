package com.bitaqaty.reseller.ui.presentation.settlementTransaction


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.lottie.utils.Utils
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.SettlementLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogHeader
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogItems
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey300
import com.bitaqaty.reseller.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun SettlementTransactionsScreen(navController: NavController, modifier: Modifier) {
    val settlementTransactionsViewModel: SettlementTransactionsViewModel = hiltViewModel()
    var pageIndex = 1
    val settlementLogList = remember { mutableStateListOf<SettlementLog>() }
    var totalElementsCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        com.bitaqaty.reseller.utilities.Utils.getUserData()?.reseller?.id?.let {
            settlementTransactionsViewModel.settlementLog(
                it, pageIndex
            )
        }
        settlementTransactionsViewModel.viewModelScope.launch {
            settlementTransactionsViewModel.settlementLogs.collect {
                it.data?.requestsLogs?.let { it1 -> settlementLogList.addAll(it1) }
                totalElementsCount = it.data?.requestTotalCount!!
            }
        }
    }

    SettlementTransactions(settlementLogList, totalElementsCount, onTransactionRequestClick = {
        navController.navigate(
            Screen.SettlementRequestScreen.route
        )
    }, onClick = {
        com.bitaqaty.reseller.utilities.Utils.getUserData()?.reseller?.id?.let {
            settlementTransactionsViewModel.settlementLog(
                it, ++pageIndex
            )
        }
    })
}

@Composable
fun SettlementTransactions(
    settlementLog:
    SnapshotStateList<SettlementLog>, totalElementsCount: Int, onClick: () -> Unit,
    onTransactionRequestClick: () -> Unit
) {
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
            onTransactionRequestClick()

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
            RechargeLogItems(
                false,
                totalElementsCount = totalElementsCount,
                onClick = {
                    onClick()
                }, settlementLog = settlementLog
            )
        }

    }
}

