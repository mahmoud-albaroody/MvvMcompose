package com.bitaqaty.reseller.ui.presentation.productDetails

import android.net.Uri
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.utilities.extention.toJson
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.printReceipt
import com.google.gson.Gson
import com.google.gson.JsonObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBottomSheet(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    navController: NavController,
    product: Product? = null,
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    val purchaseState by viewModel.purchaseState
    val context = LocalContext.current

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

            LaunchedEffect(key1 = isExpanded) {
                viewModel.toggleOpacity(1f)
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

                                    val totalRecommendedRetailPriceAfterVat =
                                        (product?.getRecommendedRetailPriceAfterVatDouble(qty.value))?.fmt()
                                    ProductDetail(
                                        label = stringResource(id = R.string.total_recommended_retail_price_after_vat),
                                        value = totalRecommendedRetailPriceAfterVat ?: ""
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
                        MadaPayButton(viewModel) {}
                    } else if (isBalancePayClicked && !isConfirmBalancePayClicked) {
                        ConfirmBalancePayButton(viewModel) {
                            viewModel.purchaseOrder(product = product!!)
                            isConfirmBalancePayClicked = true
                        }
                        MadaPayButton(viewModel) {}
                    } else {
                        when (purchaseState) {
                            is DataState.Loading -> Loading(color = Color.Blue)
                            is DataState.Error -> {}
                            is DataState.Success -> {

                                val transactionLogList =
                                    (purchaseState as DataState.Success).data.purchaseProductDetails
                                val commission = (purchaseState as DataState.Success).data.resellerCommission
                                PrintVatButton {
                                    transactionLogList?.forEach { purchaseDetailsState ->
                                        purchaseDetailsState.purchaseProductResponseDTO.products?.forEach { x ->
                                            x.productNameEn =
                                                purchaseDetailsState.purchaseProductResponseDTO.productNameEn
                                            x.productNameAr =
                                                purchaseDetailsState.purchaseProductResponseDTO.productNameAr
                                            x.vatAmount =
                                                purchaseDetailsState.purchaseProductResponseDTO.vatAmount
                                            x.price =
                                                purchaseDetailsState.purchaseProductResponseDTO.oneItemPriceBeforeVat
                                            x.date =
                                                purchaseDetailsState.purchaseProductResponseDTO.purchaseDateTime
                                            x.vatPercentage =
                                                purchaseDetailsState.purchaseProductResponseDTO.vatPercentage
                                            x.id =
                                                purchaseDetailsState.purchaseProductResponseDTO.productId
                                            x.merchantLogoPath =
                                                purchaseDetailsState.purchaseProductResponseDTO.merchantLogo
                                            x.skuBarcode =
                                                purchaseDetailsState.purchaseProductResponseDTO.skuBarcode
                                            x.showSkuBarcode =
                                                purchaseDetailsState.purchaseProductResponseDTO.showSKUBarcode
                                            x.vatCode =
                                                purchaseDetailsState.purchaseProductResponseDTO.vatCode
                                            x.recommendedRetailPriceAfterVAT = purchaseDetailsState.recommendedRetailPriceAfterVat?.toDouble()
                                            x.recommendedRetailPrice = purchaseDetailsState.recommendedRetailPrice.toDouble()

                                            printReceipt(x, context, isPrintVat = true)
                                        }

                                    }
                                }
                                DoneButton { onDismiss() }
                                transactionLogList?.forEach { purchaseDetailsState ->
                                    purchaseDetailsState.purchaseProductResponseDTO.products?.forEach { x ->
                                        x.productNameEn =
                                            purchaseDetailsState.purchaseProductResponseDTO.productNameEn
                                        x.productNameAr =
                                            purchaseDetailsState.purchaseProductResponseDTO.productNameAr
                                        x.vatAmount =
                                            purchaseDetailsState.purchaseProductResponseDTO.vatAmount
                                        x.price =
                                            purchaseDetailsState.purchaseProductResponseDTO.oneItemPriceBeforeVat
                                        x.date =
                                            purchaseDetailsState.purchaseProductResponseDTO.purchaseDateTime
                                        x.vatPercentage =
                                            purchaseDetailsState.purchaseProductResponseDTO.vatPercentage
                                        x.id =
                                            purchaseDetailsState.purchaseProductResponseDTO.productId
                                        x.merchantLogoPath =
                                            purchaseDetailsState.purchaseProductResponseDTO.merchantLogo
                                        x.skuBarcode =
                                            purchaseDetailsState.purchaseProductResponseDTO.skuBarcode
                                        x.showSkuBarcode =
                                            purchaseDetailsState.purchaseProductResponseDTO.showSKUBarcode
                                        x.vatCode =
                                            purchaseDetailsState.purchaseProductResponseDTO.vatCode
                                        printReceipt(x, context, isPrintVat = false)

                                        val jsonObject = JsonObject()
                                        jsonObject.addProperty("commission", commission)

                                        navController.navigate("successfulPurchaseScreen/${Uri.encode(jsonObject.toString())}")
                                    }
                                }

                            }
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

