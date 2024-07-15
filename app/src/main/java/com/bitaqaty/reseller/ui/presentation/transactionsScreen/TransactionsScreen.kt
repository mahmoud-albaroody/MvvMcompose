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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun TransactionsScreen(navController: NavController, modifier: Modifier) {
    val transactionsViewModel: TransactionsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    screen(onFilterClick = {
        navController.navigate(Screen.ApplyFilterScreen.route)
    })
}

//@Preview
@Composable
fun screen(onFilterClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Transactions()
        Box(Modifier.align(Alignment.BottomEnd)) {
            Filter(onFilterClick = {
                onFilterClick.invoke()
            })
        }

    }
}

@Composable
fun Transactions() {
    LazyColumn(
        Modifier
            .background(Color.White), content = {
            items(10) {
                TransactionsItem()
            }
        })
}

@Composable
fun TransactionsItem() {
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
            Image(
                modifier = Modifier
                    .padding(Dimens.halfDefaultMargin),
                painter = painterResource(R.drawable.logo),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Playstation PSN Card 10",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.halfDefaultMargin),
                    text = "12/12/2023. 03:11:55",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp
                    ),
                )
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
                                .fillMaxWidth(),
                            text = "Support Ticket",
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
                                .fillMaxWidth(),
                            text = "Print Again",
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
                TransactionsDetails()
                TransactionsDetails()
                TransactionsDetails()
                TransactionsDetails()
            }
        }
    }
}


@Preview
@Composable
fun TransactionsDetails() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.fourDefaultMargin
            )
    ) {
        Text(
            text = "Playstation PSN Card 10",
            style = TextStyle(
                color = LightGrey400,
                fontSize = 10.sp
            ),
        )
        Text(
            modifier = Modifier
                .padding(start = Dimens.DefaultMargin),
            text = "Playstation PSN Card 10",
            style = TextStyle(
                color = FontColor,
                fontSize = 10.sp
            ),
        )
    }
}

@Composable
fun Filter(onFilterClick: () -> Unit) {

    Column(Modifier.background(Color.White)) {
        Divider(
            modifier = Modifier
                .height(Dimens.DefaultMargin0)
                .fillMaxWidth()  // Set the thickness of the vertical line
                .background(BebeBlue)

        )
        Card(
            Modifier
                .clickable {
                    onFilterClick.invoke()
                }
                .padding(
                    horizontal = Dimens.DefaultMargin,
                )
                .padding(top = Dimens.DefaultMargin20, bottom = Dimens.halfDefaultMargin)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.halfDefaultMargin),
            border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
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
                    text = "Filter"
                )
            }
        }
    }
}
