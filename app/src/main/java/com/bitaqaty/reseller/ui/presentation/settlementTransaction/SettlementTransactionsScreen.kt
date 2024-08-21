package com.bitaqaty.reseller.ui.presentation.settlementTransaction


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLog
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogHeader
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogItems
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogViewModel
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey300
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun SettlementTransactionsScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: SettlementTransactionsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    SettlementTransactions(
        onTransactionRequestClick = {navController.navigate(Screen.SettlementRequestScreen.route)}
    )
}

@Composable
fun SettlementTransactions(
    onTransactionRequestClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        FilterButton(backgroundTex = Blue100, text = "Transaction Request",
            iconVisibility = true, horizontalPadding =  Dimens.DefaultMargin,textColor = Color.White ) {
            onTransactionRequestClick()
        }
        RechargeLogHeader(
            LightGrey300,
            "Date/Time",
            "Method",
            "Amount",
            "Balance After",
            FontWeight.Bold,
            textAlign = TextAlign.Center, false
        )

        Box(androidx.compose.ui.Modifier.height(screenHeight * 0.68f)) {
            RechargeLogItems(false)
        }

    }
}

@Preview
@Composable
fun SettlementTransactionsPreview(){
    SettlementTransactions(onTransactionRequestClick = {})
}