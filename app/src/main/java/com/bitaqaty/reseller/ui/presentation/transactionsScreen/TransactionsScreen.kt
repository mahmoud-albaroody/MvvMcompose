package com.bitaqaty.reseller.ui.presentation.transactionsScreen


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils


@Composable
fun TransactionsScreen(navController: NavController, modifier: Modifier) {
    val transactionsViewModel: TransactionsViewModel = hiltViewModel()
    val transactionLogList = remember { mutableStateListOf<TransactionLog>() }
    LaunchedEffect(key1 = true) {
        transactionsViewModel.transactionsLog()
        transactionsViewModel.transactionLogs.collect {
            transactionLogList.clear()
            transactionLogList.addAll(it.transactionLogList)

        }
    }

    screen(transactionLogList = transactionLogList) {
        navController.navigate(Screen.ApplyFilterScreen.route.plus(
            Screen.ApplyFilterScreen.objectName
                    + "TransactionLog"
        ))
    }
}

//@Preview
@Composable
fun screen(transactionLogList: List<TransactionLog>, onFilterClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Transactions(transactionLogList)
        Box(Modifier.align(Alignment.BottomEnd)) {
            Filter(onFilterClick = {
                onFilterClick.invoke()
            })
        }

    }
}

@Composable
fun Transactions(transactionLogList: List<TransactionLog>) {
    LazyColumn(
        Modifier
            .background(White), content = {
            items(transactionLogList) {
                TransactionsItem(it)
            }
        })
}

@Composable
fun TransactionsItem(transactionLog: TransactionLog) {
    var viewDetails by remember { mutableStateOf(false) }
    var arrow = R.drawable.ic_forward_arrow
    Card(
        Modifier
            .fillMaxWidth()

            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = Dimens.DefaultMargin
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
        ) {
//            Image(
//                modifier = Modifier
//                    .padding(Dimens.halfDefaultMargin),
//                painter = painterResource(R.drawable.logo),
//                contentDescription = ""
//            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = transactionLog.merchantLogoPath
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp, 100.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.padding8)
                    .weight(4f),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = transactionLog.getProductName(),
                    style = TextStyle(
                        color = Black,
                        fontSize = 16.sp
                    ),
                )
                transactionLog.date?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.halfDefaultMargin),
                        text = it,
                        style = TextStyle(
                            color = Black,
                            fontSize = 12.sp
                        ),
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_support_tiket),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = Dimens.padding4),
                            text = stringResource(R.string.support_ticket),
                            style = TextStyle(color = BebeBlue, fontSize = 11.sp),
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier,
                            painter = painterResource(R.drawable.ic_print),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = Dimens.padding4),
                            text = stringResource(R.string.print_again),
                            style = TextStyle(color = BebeBlue, fontSize = 11.sp),
                        )
                    }
                }
            }

            Image(
                modifier = Modifier
                    .weight(1f),
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
                    stringResource(R.string.TLogTransID),
                    transactionLog.transactionID
                )
                TransactionsDetails(
                    stringResource(R.string.TLogCostPrice),
                    transactionLog.getCheckingPrice()
                )
                TransactionsDetails(
                    stringResource(R.string.TLogVATAmount),
                    transactionLog.getCheckingVatAmount()
                )
                TransactionsDetails(
                    stringResource(R.string.recommended_retail_price),
                    transactionLog.getCheckingVatAmount()
                )
                TransactionsDetails(
                    stringResource(R.string.VAT_on_recommended),
                    transactionLog.getCheckingVatOnRecommendedRetailPrice()
                )
                TransactionsDetails(
                    stringResource(R.string.total_vat),
                    transactionLog.getCheckingTotalVat()
                )
                TransactionsDetails(
                    stringResource(R.string.recommended_retail_price_after_vat),
                    transactionLog.getCheckingRecommendedRetailPriceAfterVAT()
                )
                TransactionsDetails(
                    stringResource(R.string.balance_after),
                    transactionLog.getCheckingBalanceAfter()
                )
                TransactionsDetails(
                    stringResource(R.string.expected_profit),
                    transactionLog.getCheckingExpectedProfit()
                )
                TransactionsDetails(
                    stringResource(R.string.TLogSerialNo),
                    Utils.fixSecretsForAr(transactionLog.productSerial).toString()
                )
                TransactionsDetails(
                    transactionLog.getProductUserNameTitle(),
                    transactionLog.productUserName?.let { Utils.fixSecretsForAr(it).toString() }
                )
                TransactionsDetails(
                    transactionLog.getProductSecretTitle(),
                    Utils.fixSecretsForAr(transactionLog.productSecret).toString()
                )
                TransactionsDetails(
                    stringResource(R.string.purchase_expiry),
                    transactionLog.itemExpirationDate
                )
                TransactionsDetails(
                    stringResource(R.string.TLogUserName),
                    transactionLog.subReselleraccount
                )
                TransactionsDetails(
                    stringResource(R.string.payment_method),
                    transactionLog.getPaymentMethod()
                )
                TransactionsDetails(
                    stringResource(R.string.TLogSerialNo),
                    transactionLog.productSerial
                )

            }
        }
    }
}


@Composable
fun TransactionsDetails(title: String, value: String? = null) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.fourDefaultMargin
            )
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = LightGrey400,
                fontSize = 10.sp
            ),
        )
        Text(
            modifier = Modifier
                .padding(start = Dimens.DefaultMargin),
            text = value ?: "",
            style = TextStyle(
                color = FontColor,
                fontSize = 10.sp
            ),
        )
    }
}

@Composable
fun Filter(onFilterClick: () -> Unit) {
    Column(Modifier.background(White)) {
        Divider(
            modifier = Modifier
                .height(Dimens.DefaultMargin0)
                .fillMaxWidth()  // Set the thickness of the vertical line
                .background(BebeBlue)

        )
        Card(
            Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                )
                .padding(
                    top = Dimens.DefaultMargin20,
                    bottom = Dimens.halfDefaultMargin
                )
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.halfDefaultMargin),
            border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onFilterClick.invoke()
                    }
                    .padding(
                        horizontal = Dimens.DefaultMargin,
                        vertical = Dimens.DefaultMargin20
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(start = Dimens.halfDefaultMargin),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = BebeBlue,
                        fontSize = 14.sp,
                    ),
                    text = stringResource(id = R.string.filter)
                )
            }
        }
    }
}
