package com.bitaqaty.reseller.ui.presentation.transactionsScreen


import CTOS.CtPrint
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.ui.component.ReceiptVatComponent
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.isMadaApp
import com.bitaqaty.reseller.utilities.Utils.view2Bitmap
import com.bitaqaty.reseller.utilities.doPrinting
import com.bitaqaty.reseller.utilities.initNearPay
import com.bitaqaty.reseller.utilities.printReceipt
import com.bitaqaty.reseller.utilities.printTransaction
import io.nearpay.sdk.NearPay
import io.nearpay.sdk.utils.enums.AuthenticationData
import io.nearpay.sdk.utils.enums.GetDataFailure
import io.nearpay.sdk.utils.enums.TransactionData
import io.nearpay.sdk.utils.listeners.GetTransactionListener
import io.nearpay.sdk.utils.toImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


@Composable
fun TransactionsScreen(navController: NavController, modifier: Modifier, obj: JSONObject?) {
    val transactionsViewModel: TransactionsViewModel = hiltViewModel()
    val transactionLogList = remember { mutableStateListOf<TransactionLog>() }
    var accountNo: Int? = Utils.getUserData()?.reseller?.id
    var channel: String? = null
    var paymentMethod: String? = null
    var selectedDateTo: String? = null
    var selectedDateFrom: String? = null
    var serialNo: String? = null
    var pinCode: String? = null
    var showTotal: Boolean = false
    var isPrinted: String? = null
    var totalElementsCount by remember { mutableIntStateOf(0) }

    var searchPeriod: String? = Globals.DATE.CURRENT_MONTH.value
    val transactionRequestBody = TransactionRequestBody()


    obj?.let {

        if (obj.getInt("accountNo") != 0) {
            accountNo = obj.get("accountNo") as Int?
        }
        if (!obj.getString("channel").isNullOrEmpty()) {
            channel = obj.getString("channel")
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
        if (!obj.getString("serialNo").isNullOrEmpty()) {
            serialNo = obj.getString("serialNo")
        }
        if (!obj.getString("pinCode").isNullOrEmpty()) {
            pinCode = obj.getString("pinCode")
        }
        if (!obj.getString("isPrinted").isNullOrEmpty()) {
            isPrinted = obj.getString("isPrinted")
        }
        if (obj.getBoolean("showTotal")) {
            showTotal = obj.getBoolean("showTotal")
        }
    }
    LaunchedEffect(key1 = true) {
        transactionRequestBody.subAccountId = accountNo
        transactionRequestBody.pageNumber = 1
        transactionRequestBody.pageSize = Globals.PAGE_SIZE
        transactionRequestBody.serialNo = serialNo
        transactionRequestBody.pinCode = pinCode
        transactionRequestBody.channel = channel
        transactionRequestBody.showTotal = showTotal
        transactionRequestBody.isPrinted = isPrinted
        transactionRequestBody.paymentMethod = paymentMethod
        transactionRequestBody.customDateTo = selectedDateTo
        transactionRequestBody.customDateFrom = selectedDateFrom
        transactionRequestBody.searchPeriod = searchPeriod
        transactionsViewModel.transactionsLog(transactionRequestBody)
        transactionsViewModel.transactionLogs.collect {
            //   transactionLogList.clear()
            it.data?.transactionLogList?.let { it1 ->
                transactionLogList.addAll(it1)
            }
            totalElementsCount = it.data?.totalElementsCount!!

        }
    }

    screen(
        transactionLogList = transactionLogList,
        totalElementsCount = totalElementsCount,
        onClick = {
            transactionRequestBody.pageNumber++
            transactionsViewModel.transactionsLog(transactionRequestBody)
        },
        onFilterClick = {
            navController.navigate(
                Screen.ApplyFilterScreen.route.plus(
                    Screen.ApplyFilterScreen.objectName
                            + "TransactionLog"
                )
            )
        })
}

//@Preview
@Composable
fun screen(
    transactionLogList: List<TransactionLog>, totalElementsCount: Int,
    onFilterClick: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Transactions(transactionLogList, totalElementsCount) {
            onClick()
        }

        Box(Modifier.align(Alignment.BottomEnd)) {
            Filter(onFilterClick = {
                onFilterClick.invoke()
            })
        }

    }
}


@Composable
fun Transactions(
    transactionLogList: List<TransactionLog>,
    totalElementsCount: Int,
    onClick: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier =
    Modifier
        .background(White), content = {
        items(transactionLogList) {
            TransactionsItem(it)
        }
        if (totalElementsCount > transactionLogList.size)
            item {
                if (transactionLogList.isNotEmpty()) {
                    onClick()
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(14.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

            }
    })

}

@Composable
fun TransactionsItem(transactionLog: TransactionLog) {
    var viewDetails by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var arrow = if (transactionLog.visible) {
        R.drawable.ic_drop_down_arrow
    } else {
        R.drawable.ic_forward_arrow
    }

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
                    viewDetails = !viewDetails
                    transactionLog.visible = !transactionLog.visible
                    arrow = if (transactionLog.visible) {
                        R.drawable.ic_drop_down_arrow
                    } else {
                        R.drawable.ic_forward_arrow
                    }
                }
                .padding(Dimens.halfDefaultMargin),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = transactionLog.getMerchantLogo()
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.halfDefaultMargin),
                    text = transactionLog.getCheckingDate(),
                    style = TextStyle(
                        color = Black,
                        fontSize = 12.sp
                    ),
                )
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
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
                                .padding(start = Dimens.padding16)
                                .weight(1f)
                                .clickable {
                                    printReceipt(transactionLog, context, false)
                                },
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

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.padding8)
                                .weight(1f)
                                .clickable {
                                    printReceipt(transactionLog, context, true)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_print),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = Dimens.padding4),
                                text = stringResource(R.string.print_vat),
                                style = TextStyle(color = BebeBlue, fontSize = 11.sp),
                            )
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = Dimens.padding8, start = Dimens.padding16)
                                .clickable {
                                    printTransactionById(
                                        transactionLog,
                                        context.initNearPay(),
                                        context
                                    )
                                },
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
                                text = stringResource(R.string.print_mada),
                                style = TextStyle(color = BebeBlue, fontSize = 11.sp),
                            )
                        }
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
        viewDetails
        if (transactionLog.visible) {
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

fun printTransactionById(transactionLog: TransactionLog, nearPay: NearPay, context: Context) {
    transactionLog.transactionUUID?.let {
        nearPay.getTransactionByUuid(it, false, 0L, object :
            GetTransactionListener {
            override fun onSuccess(transactionData: TransactionData) {
                transactionData.receipts?.get(0)?.toImage(
                    context,
                    380, 12
                ) { transactionBitmap ->
                    transactionBitmap?.let { it1 -> printTransaction(it1) }
                }
            }

            override fun onFailure(getDataFailure: GetDataFailure) {
                when (getDataFailure) {
                    is GetDataFailure.AuthenticationFailed -> {
                        // when the Authentication is failed
                        // You can use the following method to update your JWT
                        nearPay.updateAuthentication(AuthenticationData.Jwt("JWT HERE"))
                    }

                    is GetDataFailure.FailureMessage -> {
                        // when there is FailureMessage
                    }

                    is GetDataFailure.GeneralFailure -> {
                        // when there is general error
                    }

                    is GetDataFailure.InvalidStatus -> {
                        // you can get the status using reconcileFailure.status
                    }
                }
            }
        })
    }
}



