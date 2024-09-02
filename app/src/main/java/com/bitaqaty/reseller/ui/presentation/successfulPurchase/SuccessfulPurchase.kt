package com.bitaqaty.reseller.ui.presentation.successfulPurchase

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.presentation.productDetails.setDataProduct
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LiteBlue
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.printReceipt
import com.google.gson.Gson
import org.json.JSONObject

@Composable
fun SuccessfulPurchaseScreen(
    modifier: Modifier,
    navController: NavController,
    transaction: PurchaseResponse
) {
    val successfulPurchaseViewModel: SuccessfulPurchaseViewModel = hiltViewModel()

    ProductDetails(
        transaction = transaction,
        navController = navController
    )
}

//@Preview
@Composable
fun ProductDetails(
    transaction: PurchaseResponse,
    navController: NavController
) {

    val context = LocalContext.current
    val product =
        transaction.purchaseResponseAndRecommendedPriceList?.get(0)

    Column(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.DefaultMargin,
                    horizontal = Dimens.DefaultMargin20
                ),
            shape = RoundedCornerShape(Dimens.DefaultMargin10),
            colors = CardDefaults.cardColors(containerColor = LiteBlue)
        ) {
            if (transaction.resellerCommission != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.DefaultMargin),
                    textAlign = TextAlign.Center,
                    text = (stringResource(id = R.string.your_commission)+" "+ transaction.resellerCommission)
                        ?: "",
                    color = BebeBlue
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(Dimens.bitaqatyLogo),
                shape = RoundedCornerShape(
                    topEndPercent = 20,
                    bottomStartPercent = 20,
                    bottomEndPercent = 20
                ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ImageLoader(
                        modifier = Modifier
                            .size(Dimens.bitaqatyLogo),
                        imgUrl = product?.purchaseProductResponseDTO?.merchantLogo,
                        errorImg = R.drawable.no_image,
                        isCircle = false
                    )
                }
            }
            Column(
                Modifier
                    .padding(horizontal = Dimens.halfDefaultMargin)
                    .padding(top = Dimens.halfDefaultMargin)
            ) {
                Text(
                    text = product?.purchaseProductResponseDTO?.getProductName() ?: ""
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product?.purchaseProductResponseDTO?.purchaseDate ?: "",
                        style = TextStyle(fontSize = 12.sp, color = LightGrey400)
                    )
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = Dimens.DefaultMargin),
//                        text = "",//"3:41 PM",
//                        style = TextStyle(fontSize = 12.sp, color = LightGrey400)
//                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.item_quantity),
                        style = TextStyle(fontSize = 10.sp, color = LightGrey400)
                    )
                    Text(
                        text = product?.purchaseProductResponseDTO?.itemsCount.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.halfDefaultMargin),
                        style = TextStyle(fontSize = 10.sp, color = FontColor)
                    )
                }

                Column(
                    Modifier.padding(
                        top = Dimens.padding30,
                        bottom = Dimens.halfDefaultMargin
                    )
                ) {
                    Recommended(
                        stringResource(id = R.string.recommended_retail_price),
                        product?.getRecommendedRetailPriceWithCurrency() ?: ""
                    )
                    Recommended(
                        stringResource(id = R.string.total_recommended_retail_price),
                        transaction.getTotalRecommendedRetailPriceWithCurrency()
                    )
                    Recommended(
                        stringResource(id = R.string.total_recommended_retail_price_after_vat),
                        transaction.getTotalRecommendedRetailAfterVatWithCurrency()
                    )
                }
            }
        }
        Row(Modifier.padding(horizontal = Dimens.padding16)) {
            Box(
                Modifier
                    .padding(top = Dimens.DefaultMargin20)
                    .weight(1f)
            ) {
                FilterButton(
                    backgroundTex = Blue100,
                    text = stringResource(id = R.string.print),
                    iconVisibility = false,
                    textColor = White,
                    horizontalPadding = Dimens.DefaultMargin,
                    onApplyFilterClick = {
                        setDataProduct(transaction, context, isPrintVat = false,onComplete = {

                        })
                    }
                )
            }
            Box(
                Modifier
                    .padding(top = Dimens.DefaultMargin20)
                    .weight(1f)
            ) {
                FilterButton(
                    backgroundTex = White,
                    text = stringResource(id = R.string.print_vat),
                    iconVisibility = false,
                    textColor = Blue100,
                    haveBorder = true, horizontalPadding = Dimens.DefaultMargin,
                    onApplyFilterClick = {
                        setDataProduct(transaction, context, isPrintVat = true,onComplete = {

                        })
                    }
                )
            }
        }
        product?.purchaseProductResponseDTO?.products?.get(0)?.let {
            Report(
                serialNum = it.productSerial,
                password = it.productPassword ?: "",
                skuBarcode = product.purchaseProductResponseDTO.skuBarcode ?: ""
            )
        }


    }
}


@Composable
fun Recommended(text: String, text2: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = Dimens.halfDefaultMargin)
            .padding(end = Dimens.DefaultMargin),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 10.sp, color = LightGrey400)
        )
        Text(
            text = text2,
            style = TextStyle(
                fontSize = 11.sp, color = FontColor
            )
        )
    }
}

//@Preview
@Composable
fun Report(
    serialNum: String,
    password: String,
    skuBarcode: String
) {
    Log.e("ssssss",skuBarcode)
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = Dimens.DefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)

    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.halfDefaultMargin
                )
                .padding(start = Dimens.DefaultMargin)
        ) {
            Recommended(stringResource(id = R.string.TLogSerialNo), serialNum)
            Recommended(stringResource(id = R.string.password), password)
            Barcode(skuBarcode = skuBarcode)
        }
    }
}

@Composable
fun Barcode(
    skuBarcode: String,
    modifier: Modifier = Modifier
) {
    val widthPixels = 380
    val heightPixels = 60

    val barcodeBitmap = remember(skuBarcode, widthPixels, heightPixels) {
        Utils.createBarcodeBitmap(
            barcodeValue = skuBarcode,
            widthPixels = widthPixels,
            heightPixels = heightPixels
        )
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = Dimens.halfDefaultMargin),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.sku_code),
            style = TextStyle(fontSize = 10.sp, color = LightGrey400)
        )
        Image(
            bitmap = barcodeBitmap.asImageBitmap(),
            contentDescription = stringResource(id = R.string.sku_code),
            modifier = modifier
        )
    }
}