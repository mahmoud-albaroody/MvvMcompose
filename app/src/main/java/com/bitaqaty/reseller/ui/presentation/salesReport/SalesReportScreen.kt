package com.bitaqaty.reseller.ui.presentation.salesReport

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.Report
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.TransactionsDetails
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Black
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.Green
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.ui.theme.clickedMerchant
import com.bitaqaty.reseller.utilities.Utils
import kotlinx.coroutines.launch

@Composable
fun SalesReportScreen(navController: NavController, modifier: Modifier) {
    val salesReportViewModel: SalesReportViewModel = hiltViewModel()

    var report by remember { mutableStateOf(ReportLog()) }

    LaunchedEffect(key1 = true) {
        salesReportViewModel.getSalesReportList(1, -1, -1, true)
        salesReportViewModel.viewModelScope.launch {
            salesReportViewModel.getReport.collect {
                report = it

            }
        }

    }
    SalesReport(report, onFilterClick = {
        navController.navigate(
            Screen.ApplyFilterScreen.route.plus(
                Screen.ApplyFilterScreen.objectName
                        + "SalesReport"
            )
        )
    })
}


@Composable
fun SalesReport(report: ReportLog, onFilterClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val reportLogList = remember { mutableStateListOf<Report>() }

    reportLogList.clear()
    report.elements?.let { it1 -> reportLogList.addAll(it1) }
    Column(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(Modifier.height(screenHeight * 0.67f)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                PrintExportButton()
                SalesReportDetails(report)
                LazyColumn(
                    Modifier
                        .height(300.dp)
                        .background(White),
                    content = {
                        items(reportLogList) { report ->
                            SalesReportItem(report)
                        }
                    })
            }
        }
        Box(Modifier.height(screenHeight * 0.13f)) {
            Filter(
                onFilterClick = {
                    onFilterClick.invoke()
                })
        }

    }
}

@Preview
@Composable
fun PrintExportButton() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.weight(1f)) {
            FilterButton(
                backgroundTex = Green,
                text = "Print",
                iconVisibility = true,
                textColor = White,
                horizontalPadding = Dimens.halfDefaultMargin,
            ) {

            }
        }
        Box(Modifier.weight(1f)) {
            FilterButton(
                backgroundTex = clickedMerchant,
                text = "Export",
                iconVisibility = true,
                textColor = White,
                horizontalPadding = Dimens.halfDefaultMargin,
            ) {

            }
        }
    }
}

@Composable
fun SalesReportDetails(report: ReportLog) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding8)
    ) {
        SalesReportDetailsCell(
            stringResource(id = R.string.current_balance),
            Utils.getUserData()?.reseller?.balance.toString() ?: "", icon = R.drawable.ic_coins
        )
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.report_no_of_transactions),
                    report.numberOfTransactions.toString(), icon = R.drawable.ic_calco
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.total_recommended_retail_price),
                    report.getTotalRecommendedPrice(),
                    icon = R.drawable.ic_calco
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.total_mada_alahly_commission),
                    report.getMadaCommission(),
                    R.drawable.ic_balance
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.total_balance_commission),
                    report.getCheckingBalanceCommission(),
                    R.drawable.ic_balance
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.total_cost_price),
                    report.getCheckingTransactionsTotalAmount(), R.drawable.ic_calco
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    stringResource(id = R.string.total_expected_profit),
                    report.getTotalExpectedProfit(),
                    R.drawable.ic_balance
                )
            }
        }
    }
}


@Composable
fun SalesReportDetailsCell(
    text: String, balance: String, icon: Int
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.fourDefaultMargin,
                vertical = Dimens.fourDefaultMargin
            )
            .clickable {
                // onItemClick.invoke()
            },
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = White)

    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding14,
                        vertical = Dimens.defaultMargin6
                    )
                    .weight(4f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    minLines = 2,
                    maxLines = 2,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Blue100, fontSize = 11.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.padding4),
                    text = balance,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        color = FontColor,
                        fontSize = 14.sp, fontWeight = FontWeight.Bold
                    ),
                )
            }



            Image(
                modifier = Modifier
                    .padding(end = Dimens.padding8)
                    .weight(0.9f),
                painter = painterResource(icon),
                contentDescription = ""
            )

        }
    }
}


@Composable
fun SalesReportItem(report: Report) {
    var viewDetails by remember { mutableStateOf(false) }
    var arrow = R.drawable.ic_forward_arrow
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = Dimens.padding14
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = LightGrey100)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    arrow = if (viewDetails) {
                        R.drawable.ic_forward_arrow
                    } else {
                        R.drawable.ic_drop_down_arrow
                    }
                    viewDetails = !viewDetails
                }
                .padding(Dimens.halfDefaultMargin),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(6f)
            ) {
                Image(
                    modifier = Modifier
                        .padding(Dimens.halfDefaultMargin)
                        .size(25.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = ""
                )
                Text(
                    text = report.getProductName(),
                    style = TextStyle(
                        color = Black,
                        fontSize = 14.sp
                    ),
                )
            }
            Divider(
                modifier = Modifier
                    .padding(horizontal = Dimens.DefaultMargin10)
                    .width(Dimens.DefaultMargin0)
                    .height(Dimens.padding50)
                    .background(BebeBlue)

            )

            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.costAfterVat),
                    style = TextStyle(
                        color = LightGrey400,
                        fontSize = 14.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Dimens.halfDefaultMargin),
                    text = report.price(),
                    style = TextStyle(
                        color = Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

            }
            Image(
                modifier = Modifier
                    .padding(horizontal = Dimens.halfDefaultMargin)
                    .size(15.dp),
                painter = painterResource(arrow),
                contentDescription = ""
            )

        }
        if (viewDetails) {
            Column(
                Modifier.padding(
                    bottom = Dimens.DefaultMargin
                )
            ) {
                TransactionsDetails(
                    stringResource(id = R.string.report_username),
                    report.subAccountName
                )
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.halfDefaultMargin,
                            horizontal = Dimens.DefaultMargin
                        ),
                    shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                    border = BorderStroke(0.25.dp, LightGrey200),
                    colors = CardDefaults.cardColors(containerColor = LightGrey100)

                ) {
                    SalesReportItemDetails(
                        stringResource(id = R.string.no_of_trans),
                        report.numberOfTrans.toString()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.service),
                        report.getMerchantName()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.VAT_on_recommended),
                        report.getVatOnRecommendedRetailPrice()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.total_cost_per_item),
                        report.getTotalCostPerItem()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.total_cost_price),
                        report.totalTransAmount()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.recommended_retail_price_after_vat),
                        report.getRecommendedPrice()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.total_recommended_retail_price),
                        report.getTotalRecommendedPrice()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.payment_method),
                        report.getPaymentMethod()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.total_expected_profit),
                        report.getTotalExpectedProfit()
                    )

                }
            }
        }
    }
}

@Composable
fun SalesReportItemDetails(title: String, valuee: String) {
    Row(
        Modifier
            .background(White)
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.halfDefaultMargin,
                vertical = Dimens.fourDefaultMargin
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = Dimens.fourDefaultMargin
                ),
            text = title,
            style = TextStyle(
                color = LightGrey200,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            ),
        )
        Text(
            modifier = Modifier
                .padding(
                    start = Dimens.DefaultMargin,
                    end = Dimens.padding30,
                    top = Dimens.fourDefaultMargin
                ),
            text = valuee,
            style = TextStyle(
                color = FontColor,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            ),
        )
    }
}