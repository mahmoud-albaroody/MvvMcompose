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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.TransactionsDetails
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.Green
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.clickedMerchant

@Composable
fun SalesReportScreen(navController: NavController, modifier: Modifier) {
    val salesReportViewModel: SalesReportViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    SalesReport()
}


@Preview
@Composable
fun SalesReport() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(Modifier.height(screenHeight * 0.67f)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                PrintExportButton()
                SalesReportDetails()
                LazyColumn(
                    Modifier
                        .height(300.dp)
                        .background(Color.White),
                    content = {
                        items(10) {
                            SalesReportItem()
                        }
                    })
            }
        }
        Box(Modifier.height(screenHeight * 0.2f)) {
            Filter(
                onFilterClick = {
                    // onFilterClick.invoke()
                })
        }

    }
}

@Preview
@Composable
fun PrintExportButton() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.weight(1f)) {
            FilterButton(
                backgroundTex = Green,
                text = "Print",
                iconVisibility = true,
                textColor = Color.White
            ) {

            }
        }
        Box(Modifier.weight(1f)) {
            FilterButton(
                backgroundTex = clickedMerchant,
                text = "Export",
                iconVisibility = true,
                textColor = Color.White
            ) {

            }
        }
    }
}

@Preview
@Composable
fun SalesReportDetails() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.halfDefaultMargin)
    ) {
        SalesReportDetailsCell(
            "Current Balance",
            838.88, icon = R.drawable.ic_coins
        )
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "No. of Transactions\n",
                    0.32, icon = R.drawable.ic_calco
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "Total Recommended\n" +
                            "Retail Price", 0.32, icon = R.drawable.ic_calco
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "Total MADA Alahly\n" +
                            "Commission", 0.32, R.drawable.ic_balance
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "Total Balance\n"
                            + "Commission", 0.32, R.drawable.ic_balance
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "Total Cost Price\n",
                    0.32, R.drawable.ic_calco
                )
            }
            Box((Modifier.weight(1f))) {
                SalesReportDetailsCell(
                    "Total Expected\n" +
                            "Commission", 0.32, R.drawable.ic_balance
                )
            }
        }
    }
}


@Composable
fun SalesReportDetailsCell(
    text: String, balance: Double, icon: Int
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.halfDefaultMargin,
                vertical = Dimens.fourDefaultMargin
            )
            .clickable {
                // onItemClick.invoke()
            },
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.White)

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
                        horizontal = Dimens.DefaultMargin,
                        vertical = Dimens.defaultMargin6
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        color = Blue100, fontSize = 12.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.defaultMargin6),
                    text = balance.toString(),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp, fontWeight = FontWeight.Bold
                    ),
                )
            }



            Image(
                modifier = Modifier.padding(end = Dimens.DefaultMargin10).size(25.dp),
                painter = painterResource(icon),
                contentDescription = ""
            )

        }
    }
}

@Preview
@Composable
fun SalesReportItem() {
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
                Image(
                    modifier = Modifier
                        .padding(Dimens.halfDefaultMargin)
                        .size(25.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = ""
                )
                Text(
                    text = "Playstation PSN Card 10",
                    style = TextStyle(
                        color = Color.Black,
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
                    text = "Playstation PSN Card 10",
                    style = TextStyle(
                        color = LightGrey400,
                        fontSize = 14.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Dimens.halfDefaultMargin),
                    text = "19.68",
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
                Modifier.padding(
                    bottom = Dimens.DefaultMargin
                )
            ) {
                TransactionsDetails()
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
                    SalesReportItemDetails()
                    SalesReportItemDetails()
                    SalesReportItemDetails()
                    SalesReportItemDetails()
                }
            }
        }
    }
}

@Preview
@Composable
fun SalesReportItemDetails() {
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
            text = "Playstation PSN Card 10",
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
            text = "10",
            style = TextStyle(
                color = FontColor,
                fontSize = 10.sp
            ),
        )
    }
}