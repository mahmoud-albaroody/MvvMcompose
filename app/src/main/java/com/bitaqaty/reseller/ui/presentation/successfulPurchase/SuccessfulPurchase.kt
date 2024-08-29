package com.bitaqaty.reseller.ui.presentation.successfulPurchase

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LiteBlue
import com.bitaqaty.reseller.utilities.Utils
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

    jsonObject.let {
        commission = it.getString("commission")
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
                    text = commission, color = BebeBlue
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(Dimens.bitaqatyLogo)
                    .background(Color.White),
                painter = painterResource(id = R.drawable.bitaqaty_logo),
                contentDescription = "Logo",
            )
            Column(
                Modifier
                    .padding(horizontal = Dimens.halfDefaultMargin)
                    .padding(top = Dimens.halfDefaultMargin)
            ) {
                Text(text = "Apple & iTunes Giftcard \$10 (US Store)")
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "12/12/2024",
                        style = TextStyle(fontSize = 12.sp, color = LightGrey400)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = Dimens.DefaultMargin),
                        text = "3:41 PM",
                        style = TextStyle(fontSize = 12.sp, color = LightGrey400)
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        style = TextStyle(fontSize = 10.sp, color = LightGrey400)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.halfDefaultMargin),
                        text = "1",
                        style = TextStyle(fontSize = 10.sp, color = FontColor)
                    )
                }

                Column(
                    Modifier.padding(
                        top = Dimens.padding30,
                        bottom = Dimens.halfDefaultMargin
                    )
                ) {
                    Recommended("Recommended Retail Price", "72.08 SAR")
                    Recommended("Recommended Retail Price", "72.08 SAR")
                    Recommended("Recommended Retail Price", "72.08 SAR")
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
        Report()
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

@Preview
@Composable
fun Report() {
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
            Recommended("Serial Number", "Bravooo12355")
            Recommended("Password", "Bravooo12355")
            Recommended("SKU Barcode", "Bravooo12355")
            Recommended("SKU Barcode", "Bravooo12355")
        }
    }
}