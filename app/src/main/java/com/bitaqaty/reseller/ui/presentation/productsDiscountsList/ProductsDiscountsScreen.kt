package com.bitaqaty.reseller.ui.presentation.productsDiscountsList

import android.util.Log
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.salesReport.PrintExportButton
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Black
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.White
import org.json.JSONObject


@Composable
fun ProductsDiscountsScreen(navController: NavController, modifier: Modifier, obj: JSONObject?) {
    val productsDiscountsViewModel: ProductsDiscountsViewModel = hiltViewModel()
    val products: SnapshotStateList<Product> = remember { mutableStateListOf() }
    var categoryId: Int = -1
    var merchantId: Int? = null
    obj?.let {
        if (obj.getInt("categoryId") != 0) {
            categoryId = obj.getInt("categoryId")
        }
        if (obj.getInt("merchantId") != 0) {
            merchantId = obj.getInt("merchantId")
        }
    }
    LaunchedEffect(key1 = true) {
        productsDiscountsViewModel
            .getProductDiscountList(
                0, categoryId,
                merchantId, "", false
            )
        productsDiscountsViewModel.getProduct.collect {
            products.clear()
            products.addAll(it.products)

        }
    }
    ProductsDiscounts(products) {
        navController.navigate(
            Screen.ApplyFilterScreen.route.plus(
                Screen.ApplyFilterScreen.objectName
                        + "productDiscount"
            )
        )
    }

}

@Composable
fun ProductsDiscounts(products: SnapshotStateList<Product>, onFilterClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        PrintExportButton() {

        }
        Box(Modifier.weight(1f)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(White),
                content = {
                    items(products) {
                        ProductsDiscountsItem(it)
                    }
                })
        }


        Filter(
            onFilterClick = {
                onFilterClick.invoke()
            })

    }
}


@Preview
@Composable
fun CancelDone() {
    PrintExportButton() {

    }

}


@Composable
fun ProductsDiscountsItem(product: Product) {
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(6f)
            ) {

                AsyncImage(
                    modifier = Modifier
                        .padding(Dimens.halfDefaultMargin)
                        .size(25.dp),
                    model = product.getImage(),
                    contentDescription = null,
                )
                Text(
                    text = product.getName(),
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
                    text = stringResource(id = R.string.costPrice),
                    style = TextStyle(
                        color = LightGrey400,
                        fontSize = 14.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Dimens.halfDefaultMargin),
                    text = product.getRecommendedRetailPriceDouble().toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

            }
            Image(
                modifier = Modifier
                    .padding(horizontal = Dimens.halfDefaultMargin),
                painter = painterResource(arrow),
                contentDescription = ""
            )

        }
        if (viewDetails) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.halfDefaultMargin)
                ) {
                    Box(Modifier.weight(1f)) {
                        ServiceItem(
                            stringResource(id = R.string.service),
                            product.getMerchantName()
                        )
                    }
                    Box(Modifier.weight(1f)) {
                        ServiceItem(
                            stringResource(id = R.string.vat_amount_per),
                            product.vatePercentage.toString()
                        )
                    }
                    Box(Modifier.weight(1f)) {
                        ServiceItem(
                            stringResource(id = R.string.costAfterVat),
                            product.getPriceAfterVat()
                        )
                    }
                }
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.DefaultMargin
                        )
                        .padding(
                            top = Dimens.fourDefaultMargin,
                            bottom = Dimens.DefaultMargin
                        ),
                    shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                    border = BorderStroke(0.25.dp, LightGrey200),
                    colors = CardDefaults.cardColors(containerColor = LightGrey100)

                ) {
                    SalesReportItemDetails(
                        stringResource(id = R.string.recommended_retail_price),
                        product.getRecommendedPrice()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.VAT_on_recommended),
                        product.vatOnRecommendedPrice ?: ""
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.total_vat),
                        product.getTotalVat().toString()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.recommended_retail_price_after_vat),
                        product.getRecommendedRetailPriceDouble().toString()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.expected_profit),
                        product.getExpectProfitDouble().toString()
                    )
                    SalesReportItemDetails(
                        stringResource(id = R.string.sku_code),
                        product.skuBarcode.toString()
                    )


                }
            }
        }
    }
}


@Composable
fun SalesReportItemDetails(title: String, value: String) {
    Row(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.halfDefaultMargin,
                vertical = Dimens.fourDefaultMargin
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = LightGrey200,
                fontSize = 10.sp
            ),
        )
        Text(
            modifier = Modifier
                .padding(
                    start = Dimens.DefaultMargin,
                    end = Dimens.padding30, top = Dimens.halfDefaultMargin
                ),
            text = value,
            style = TextStyle(
                color = FontColor,
                fontSize = 10.sp
            ),
        )
    }
}

@Composable
fun ServiceItem(name: String, valu: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.quarterDefaultMargin
            )
            .padding(
                top = Dimens.fourDefaultMargin,
                bottom = Dimens.fourDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(0.25.dp, LightGrey200),
        colors = CardDefaults.cardColors(containerColor = LightGrey100)

    ) {

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding2)
                    .padding(top = Dimens.halfDefaultMargin),
                text = name,
                minLines = 2,
                style = TextStyle(
                    color = LightGrey400,
                    fontSize = 11.sp, textAlign = TextAlign.Center
                ),
            )
            Text(
                modifier = Modifier
                    .padding(vertical = Dimens.halfDefaultMargin),
                text = valu,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

        }
    }
}