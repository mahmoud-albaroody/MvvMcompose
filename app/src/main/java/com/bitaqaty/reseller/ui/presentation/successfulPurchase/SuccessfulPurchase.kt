package com.bitaqaty.reseller.ui.presentation.successfulPurchase

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.presentation.common.ProductLogo
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LiteBlue
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.noRippleClickable
import org.json.JSONObject

@Composable
fun SuccessfulPurchaseScreen(
    modifier: Modifier,
    navController: NavController,
    navBackStack: NavBackStackEntry,
) {
    val successfulPurchaseViewModel: SuccessfulPurchaseViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    ProductDetails(
        navBackStack = navBackStack,
        navController = navController
    )
}

//@Preview
@Composable
fun ProductDetails(
    navBackStack: NavBackStackEntry,
    navController: NavController
) {
    val jsonString = navBackStack.arguments?.getString("productDetails") ?: ""
    val decodedJsonString = Uri.decode(jsonString)
    val jsonObject = JSONObject(decodedJsonString)

    var commission = ""
    var logoUrl = ""
    var productName = ""
    var date = ""
    var quantity = ""
    var recommendedPrice = ""
    var totalRecommendedPrice = ""
    var totalRecommendedPriceAfterVat = ""
    var productSerial = ""
    var productPassword = ""
    var skuBarcode = ""

    jsonObject.let {
        commission = it.getString("commission")
        logoUrl = it.getString("logo")
        productName = it.getString("name")
        date = it.getString("date")
        quantity = it.getString("quantity")
        recommendedPrice = it.getString("recommendedPrice")
        totalRecommendedPrice = it.getString("totalRecommendedPrice")
        totalRecommendedPriceAfterVat = it.getString("totalRecommendedPriceAfterVat")
        productSerial = it.getString("productSerial")
        productPassword = it.getString("productPassword")
        skuBarcode = it.getString("skuBarcode")
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
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
            if(Utils.isPartnerApp()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.DefaultMargin),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.your_commission) + commission,
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
                        imgUrl = logoUrl,
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
                Text(text = productName)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$date ",
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.halfDefaultMargin),
                        text = quantity,
                        style = TextStyle(fontSize = 10.sp, color = FontColor)
                    )
                }

                Column(
                    Modifier.padding(
                        top = Dimens.padding30,
                        bottom = Dimens.halfDefaultMargin
                    )
                ) {
                    Recommended(stringResource(id = R.string.recommended_retail_price), recommendedPrice)
                    Recommended(stringResource(id = R.string.total_recommended_retail_price), totalRecommendedPrice)
                    Recommended(stringResource(id = R.string.total_recommended_retail_price_after_vat), totalRecommendedPriceAfterVat)
                }
            }
        }
        Row {
            Box(
                Modifier
                    .padding(top = Dimens.DefaultMargin20)
                    .weight(1f)
            ) {
                FilterButton(
                    backgroundTex = Blue100, text = "Print Product",
                    iconVisibility = false, textColor = Color.White,horizontalPadding =  Dimens.DefaultMargin,
                    onApplyFilterClick = {

                    }
                )
            }
            Box(
                Modifier
                    .padding(top = Dimens.DefaultMargin20)
                    .weight(1f)
            ) {
                FilterButton(
                    backgroundTex = Color.White,
                    text = "Print VAT",
                    iconVisibility = false,
                    textColor = Blue100,
                    haveBorder = true,horizontalPadding =  Dimens.DefaultMargin,
                    onApplyFilterClick = {

                    }
                )
            }
        }
        Report(
            serialNum = productSerial,
            password = productPassword,
            skuBarcode = skuBarcode
        )
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
            .padding(top = Dimens.halfDefaultMargin)
            .padding(end = Dimens.DefaultMargin),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.sku_code),
            style = TextStyle(fontSize = 10.sp, color = LightGrey400)
        )
        Image(
            bitmap = barcodeBitmap.asImageBitmap(),
            contentDescription = "SKU Barcode",
            modifier = modifier
        )
    }
}