package com.bitaqaty.reseller.ui.presentation.productDetails

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.MainApplication
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.ui.presentation.productDetails.components.BalancePayButton
import com.bitaqaty.reseller.ui.presentation.productDetails.components.ConfirmBalancePayButton
import com.bitaqaty.reseller.ui.presentation.productDetails.components.Counter
import com.bitaqaty.reseller.ui.presentation.productDetails.components.DoneButton
import com.bitaqaty.reseller.ui.presentation.productDetails.components.MadaPayButton
import com.bitaqaty.reseller.ui.presentation.productDetails.components.PrintVatButton
import com.bitaqaty.reseller.ui.presentation.productDetails.components.ProductInfo
import com.bitaqaty.reseller.ui.theme.arial
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.noRippleClickable
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.model.PurchaseResponseProductDetails
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.recharge.rechargeWithCashIn
import com.bitaqaty.reseller.utilities.Utils.isMadaApp
import com.bitaqaty.reseller.utilities.Utils.isNearPayApp
import com.bitaqaty.reseller.utilities.initNearPay
import com.bitaqaty.reseller.utilities.madaConnectionResult
import com.bitaqaty.reseller.utilities.network.Resource
import com.bitaqaty.reseller.utilities.printReceipt
import com.bitaqaty.reseller.utilities.printTransaction
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.nearpay.sdk.utils.enums.TransactionData
import io.nearpay.sdk.utils.toImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBottomSheet(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    product: Product? = null,
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var paymentRefNumber by remember { mutableStateOf("") }
    var transaction: TransactionData? = null
    initMada(product, viewModel, paymentRefNumber)
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RectangleShape,
            dragHandle = null,
            scrimColor = Color.Black.copy(alpha = .5f),
        ) {
            var isExpanded by remember { mutableStateOf(false) }
            var isBalancePayClicked by remember { mutableStateOf(false) }
            var isConfirmBalancePayClicked by remember { mutableStateOf(false) }

            var transactionLog by remember { mutableStateOf(PurchaseResponse()) }
            var isLoading by remember { mutableStateOf(false) }
            var totalRecommendedRetailPriceAfterVat by remember { mutableDoubleStateOf(0.0) }
            LaunchedEffect(key1 = isExpanded) {
                viewModel.toggleOpacity(1f)
                viewModel.viewModelScope.launch {
                    viewModel.initPurchase.collect {
                        when (it) {
                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                isLoading = false
                                paymentRefNumber = it.data?.paymentRefNumber.toString()
                                purchaseWithPartner(totalRecommendedRetailPriceAfterVat,
                                    context,
                                    paymentRefNumber,
                                    transactionData = { transactionData ->
                                        transaction = transactionData
                                    },
                                    onCallRecharge = {
                                        product?.let {
                                            viewModel.completePurchase(
                                                arrayListOf(product),
                                                paymentRefNumber,
                                                requestStatue = transaction?.receipts?.get(0)?.status_message?.english?.toUpperCase(),
                                                cashInTransaction = transaction?.receipts?.get(0),
                                                isCart = false,
                                                isBalance = false
                                            )
                                        }
                                    })
                            }

                            is Resource.DataError -> {

                            }
                        }
                    }
                }
                viewModel.viewModelScope.launch {
                    viewModel.completePurchaseCart.collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                isLoading = true
                            }

                            is Resource.Success -> {
                                isLoading = false
                                onDismiss()
                                Log.e("sssss","sadsadsad")
                                result.data?.let {
                                    transactionLog = it
                                }
                                if (isNearPayApp() && !isBalancePayClicked) {
                                    transaction?.let {
                                        transaction?.receipts?.get(0)?.toImage(
                                            context, 380, 12
                                        ) { imageBitmap ->
                                            imageBitmap?.let { it1 -> printTransaction(it1) }
                                            setDataProduct(
                                                transactionLog, context, false, onComplete = {
                                                    transactionLog.purchaseResponseAndRecommendedPriceList?.forEach {
                                                        it.purchaseProductResponseDTO?.vatPercentage =
                                                            it.purchaseProductResponseDTO?.vatPercentage?.substringBefore(
                                                                "%"
                                                            ).toString()
                                                    }
                                                    val transactionJson =
                                                        Gson().toJson(transactionLog)
                                                    navController.navigate(
                                                        Screen.SuccessfulPurchaseScreen.route +
                                                                "?transactionLog=${transactionJson}"
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                                else if (isMadaApp()&& !isBalancePayClicked) {

                                    setDataProduct(
                                        transactionLog, context, false, onComplete = {
                                            Log.e("sssss","sadasdasd")
                                            transactionLog.purchaseResponseAndRecommendedPriceList?.forEach {
                                                it.purchaseProductResponseDTO?.vatPercentage =
                                                    it.purchaseProductResponseDTO?.vatPercentage?.substringBefore(
                                                        "%"
                                                    ).toString()
                                            }
                                            val transactionJson = Gson().toJson(transactionLog)
                                            Log.e("sssss",transactionLog.toString())
                                            navController.navigate(
                                                Screen.SuccessfulPurchaseScreen.route +
                                                        "?transactionLog=${transactionJson}"
                                            )
                                        }
                                    )
                                }
                                else {
                                    setDataProduct(
                                        transactionLog, context, false, onComplete = {
                                            transactionLog.purchaseResponseAndRecommendedPriceList?.forEach {
                                                it.purchaseProductResponseDTO?.vatPercentage =
                                                    it.purchaseProductResponseDTO?.vatPercentage?.substringBefore(
                                                        "%"
                                                    ).toString()
                                            }
                                            val transactionJson = Gson().toJson(transactionLog)
                                            navController.navigate(
                                                Screen.SuccessfulPurchaseScreen.route +
                                                        "?transactionLog=${transactionJson}"
                                            )
                                        }
                                    )
                                }

                            }

                            is Resource.DataError -> {

                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .offset(y = (20).dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .background(
                            color = Color.White, shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .noRippleClickable {
                            onDismiss()
                            viewModel.resetCounter()
                        },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = Color(0xFF1C274C)
                )
            }

            val showCost = Utils.showCost()
            val showRecommendedCost = Utils.showRecommended()
            val qty = viewModel.counter
            product?.quantity = viewModel.counter.value

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    totalRecommendedRetailPriceAfterVat =
                        (product?.getRecommendedRetailPriceAfterVatDouble(qty.value) ?: 0.0)
                    if (isExpanded) {
                        Text(
                            modifier = Modifier.padding(top = 24.dp),
                            text = "${product?.getName() ?: ""}\n",
                            fontFamily = arial,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                        )
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        ) {
                            item {
                                if (true) {
                                    val totalPrice =
                                        (product?.getPriceDouble()?.times(qty.value))?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.total_cost_price),
                                        value = totalPrice ?: ""
                                    )

                                    val vatValue =
                                        (product?.getVatDouble()?.times(qty.value))?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.vat_amount_per).replace(
                                            "Amount (%)", "${product?.vatPercentage ?: ""}%"
                                        ), value = vatValue ?: ""
                                    )

                                    val totalCostAfterVat =
                                        (product?.getPriceAfterVatDouble(qty.value))?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.total_cost_after_vat),
                                        value = totalCostAfterVat ?: ""
                                    )
                                }

                                if (true) {
                                    val recommendedRetailPrice =
                                        (product?.getRecommendedRetailPriceDouble())?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.recommended_retail_price),
                                        value = recommendedRetailPrice ?: ""
                                    )

                                    val totalRecommendedRetailPrice =
                                        (product?.getRecommendedRetailPriceDouble()
                                            ?.times(qty.value))?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.total_recommended_retail_price),
                                        value = totalRecommendedRetailPrice ?: ""
                                    )


                                    ProductDetail(
                                        label = stringResource(id = R.string.total_recommended_retail_price_after_vat),
                                        value = totalRecommendedRetailPriceAfterVat.fmt() ?: ""
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .border(width = 1.dp, color = Color.LightGray)
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(vertical = 28.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Counter(viewModel)
                    Spacer(modifier = Modifier.width(32.dp))

                    val totalAmount = if (showCost) {
                        (product?.getPriceAfterVatDouble(qty.value))?.fmt() ?: ""
                    } else {
                        (product?.getSubResellerPrice())?.fmt() ?: ""
                    }
                    ProductInfo(totalAmount = totalAmount,
                        onClickInfo = { isExpanded = !isExpanded })
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    //.padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (!isBalancePayClicked) {
                        BalancePayButton { isBalancePayClicked = true }
                        Spacer(modifier = Modifier.width(2.dp))
                        MadaPayButton(viewModel) {
                            product?.let {
                                viewModel.initPurchase(
                                    arrayListOf(it), totalRecommendedRetailPriceAfterVat.toString()
                                )
                            }
                        }
                    } else if (isBalancePayClicked && !isConfirmBalancePayClicked) {
                        ConfirmBalancePayButton(viewModel) {
                            product?.let {
                                viewModel.completePurchase(
                                    arrayListOf(it), isCart = false, isBalance = true
                                )
                            }
                            isConfirmBalancePayClicked = true
                        }
                        MadaPayButton(viewModel) {
                            product?.let {
                                viewModel.initPurchase(
                                    arrayListOf(it), totalRecommendedRetailPriceAfterVat.toString()
                                )
                            }
                        }
                    } else {
                        if (isLoading) {
                            Loading(color = Color.Blue)
                        } else {
                            PrintVatButton {
                                setDataProduct(transactionLog, context, true, onComplete = {

                                    transactionLog.purchaseResponseAndRecommendedPriceList?.forEach {
                                        it.purchaseProductResponseDTO?.vatPercentage =
                                            it.purchaseProductResponseDTO?.vatPercentage?.substringBefore(
                                                "%"
                                            ).toString()
                                    }
                                    val transactionJson = Gson().toJson(transactionLog)
                                    navController.navigate(
                                        Screen.SuccessfulPurchaseScreen.route +
                                                "?transactionLog=${transactionJson}"
                                    )
                                })
                            }
                            DoneButton { onDismiss() }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductDetail(
    label: String, value: String
) {
    Row {
        Text(
            text = label, fontFamily = arial, fontSize = 12.sp, color = Color(0xFF8D8D8D)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            fontFamily = arial,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "SAR", //Utils.getUserCurrency(),
            fontFamily = arial,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color(0xFF5F5F5F)
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
}

fun setDataProduct(
    transactionLog: PurchaseResponse,
    context: Context,
    isPrintVat: Boolean, onComplete: () -> Unit
) {
    val transactionLogList:
            ArrayList<PurchaseResponseProductDetails> = arrayListOf()
    transactionLog.purchaseResponseAndRecommendedPriceList?.let { it1 ->
        transactionLogList.addAll(it1)
    }


    transactionLogList.forEach { purchaseDetailsState ->
        purchaseDetailsState.purchaseProductResponseDTO?.products?.forEach { product ->
            product.productNameEn = purchaseDetailsState.purchaseProductResponseDTO.productNameEn
            product.productNameAr = purchaseDetailsState.purchaseProductResponseDTO.productNameAr
            product.vatAmount = purchaseDetailsState.purchaseProductResponseDTO.vatAmount
            product.price = purchaseDetailsState.purchaseProductResponseDTO.oneItemPriceBeforeVat
            product.date = purchaseDetailsState.purchaseProductResponseDTO.purchaseDateTime
            product.vatPercentage =
                purchaseDetailsState.purchaseProductResponseDTO.vatPercentage?.substringBefore("%")
            product.id = purchaseDetailsState.purchaseProductResponseDTO.productId
            product.merchantLogoPath = purchaseDetailsState.purchaseProductResponseDTO.merchantLogo
            product.skuBarcode = purchaseDetailsState.purchaseProductResponseDTO.skuBarcode
            product.showSkuBarcode = purchaseDetailsState.purchaseProductResponseDTO.showSKUBarcode
            product.vatCode = purchaseDetailsState.purchaseProductResponseDTO.vatCode
            printReceipt(product, context, isPrintVat = isPrintVat)
        }
    }
    Log.e("sssss","sadasdasdشسيشسيشس")
    onComplete()
}


private fun initMada(
    product: Product?, viewModel: ProductDetailsViewModel, paymentRefNumber: String
) {
    MainApplication.getCallBackInstance {
        it?.let { madaResponse ->
            product?.let {
                viewModel.completePurchase(
                    arrayListOf(product),
                    paymentRefNumber,
                    madaResponse,
                    isCart = false,
                    isBalance = false
                )
            }
        }
    }
}


private fun purchaseWithPartner(
    rec: Double?,
    context: Context,
    paymentRefNumber: String,
    transactionData: (TransactionData) -> Unit,
    onCallRecharge: () -> Unit
) {
    if (isMadaApp()) {
        if (rec.toString().substringAfter(".").length == 2) {
            context.madaConnectionResult(
                totalAmount = (rec!! * 10).fmt(), refName = paymentRefNumber
            )
        } else {
            context.madaConnectionResult(
                totalAmount = (rec!! * 100).fmt(), refName = paymentRefNumber
            )
        }
    } else if (isNearPayApp()) {
        if (rec.toString().substringAfter(".").length == 2) {
            rechargeWithCashIn(context.initNearPay(),
                (((rec!!.toInt() * 100).fmt()).toDouble()).toLong(),
                paymentRefNumber,
                context,
                onCallRecharge = {
                    onCallRecharge()
                },
                transactionData = {
                    transactionData(it)
                })
        } else {
            rechargeWithCashIn(context.initNearPay(),
                (((rec!!.toInt() * 100).fmt()).toDouble()).toLong(),
                paymentRefNumber,
                context,
                onCallRecharge = {
                    onCallRecharge()
                },
                transactionData = {
                    transactionData(it)
                })
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview(showBackground = false, showSystemUi = true)
//fun ProductDetailsBottomSheetPreview(){
//    val sheetState = rememberStandardBottomSheetState(
//        initialValue = SheetValue.Expanded
//    )
//    ProductDetailsBottomSheet(
//        isBottomSheetVisible = true,
//        sheetState = sheetState,
//        onDismiss = {}
//    )
//}

