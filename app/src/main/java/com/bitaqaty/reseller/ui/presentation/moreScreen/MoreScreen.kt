package com.bitaqaty.reseller.ui.presentation.moreScreen


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.applyFilter.ApplyFilter
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.profileScreen.AccountManager
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LightGrey80


@Composable
fun MoreScreen(navController: NavController, modifier: Modifier) {
    val moreViewModel: MoreViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    More(onItemClick = {
        when (it) {

            "Notifications" -> {
                navController.navigate(Screen.Notification.route)
            }

            "Edit Profile" -> {
                navController.navigate(Screen.MyProfileScreen.route)
            }

            "Reports" -> {
                  navController.navigate(Screen.SalesReportScreen.route)
            }

            "Add Balance" -> {
                navController.navigate(Screen.ChargeBalanceScreen.route)
            }

            "English" -> {
                //     navController.navigate(Screen.Notification.route)
            }

            "Settlement\n" + "Transaction" -> {
                navController.navigate(Screen.SettlementTransactionsScreen.route)
            }

            "Product \n" + "Discount List" -> {
                navController.navigate(Screen.ProductsDiscountsScreen.route)
            }

            "Logout" -> {
                navController.navigate(Screen.MainScreen.route)
            }
        }

    })
}


@Composable
fun More(onItemClick: (String) -> Unit) {

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin)
            .padding(top = Dimens.halfDefaultMargin),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            MoreItem(
                text = "Notifications",
                text2 = "3",
                textColor = Blue,
                icon = R.drawable.ic_notification,
                weight = 8f, onItemClick = {
                    onItemClick.invoke("Notifications")
                }
            )
            MoreReport(
                "Edit Profile", "Add Balance",
                text3 = "Khalid Ali", text4 = "15000,00",
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreReport(
                text3 = "Reports",
                text4 = "Reseller\n" + "Support",
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreReport(
                text3 = "Product \n" + "Discount List",
                text4 = "Settlement\n" + "Transaction",
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreItem(
                text = "Reports",
                text2 = "English",
                textColor = Blue,
                icon = R.drawable.ic_language,
                weight = 8f,
                onItemClick = {
                    onItemClick.invoke("English")
                }
            )
        }
        Column {
            Box(Modifier.padding(top = Dimens.halfDefaultMargin)) {
                Divider(
                    modifier = Modifier
                        .height(Dimens.DefaultMargin0)
                        .fillMaxWidth()  // Set the thickness of the vertical line
                        .background(LightGrey80)
                )
            }
            Box(Modifier.padding(top = Dimens.halfDefaultMargin)) {
                FilterButton(backgroundTex = Color.Red,
                    text = "Logout",
                    iconVisibility = false,
                    textColor = Color.White,
                    onApplyFilterClick = {
                        onItemClick.invoke("Logout")
                    })
            }
        }
    }
}


@Composable
fun MoreReport(
    text: String? = null, text2: String? = null,
    text3: String, text4: String, onItemClick: (String) -> Unit
) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f)) {
                MoreItem(
                    text = text3,
                    text3 = text,
                    textColor = Blue,
                    icon = R.drawable.ic_personal_profile,
                    weight = 2f,
                    onItemClick = {
                        if (text != null) {
                            onItemClick(text)
                        } else {
                            onItemClick(text3)
                        }
                    }
                )
            }
            Box(Modifier.weight(1f)) {
                MoreItem(
                    text = text4,
                    textColor = Blue,
                    text3 = text2,
                    icon = R.drawable.ic_personal_profile,
                    weight = 2f, onItemClick = {
                        if (text2 != null) {
                            onItemClick(text2)
                        } else {
                            onItemClick(text4)
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun MoreItem(
    text: String,
    text2: String? = null,
    text3: String? = null,
    textColor: Color,
    icon: Int,
    weight: Float,
    onItemClick: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.fourDefaultMargin)
            .padding(top = Dimens.halfDefaultMargin)
            .clickable {
                onItemClick.invoke()
            },
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.halfDefaultMargin),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (text2 == null) {
                Image(
                    modifier = Modifier
                        .size(Dimens.profileImage)
                        .padding(Dimens.halfDefaultMargin),
                    painter = painterResource(icon),
                    contentDescription = ""
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(Dimens.profileImage)
                        .weight(1f),
                    painter = painterResource(icon),
                    contentDescription = ""
                )
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(weight),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        style = TextStyle(
                            color = textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                    )
                    text3?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.defaultMargin6),
                            text = it,
                            style = TextStyle(
                                color = LightGrey200,
                                fontSize = 10.sp
                            ),
                        )
                    }
                }


                text2?.let {
                    var textCo: Color = Color.White
                    var color = Color.Red

                    if (text2 == "English") {
                        color = Color.Transparent
                        textCo = LightGrey200
                    }
                    Card(
                        Modifier.clip(CircleShape),
                        colors = CardDefaults.cardColors(containerColor = color)
                    ) {
                        Text(
                            modifier = Modifier.padding(Dimens.fourDefaultMargin),
                            text = it,
                            style = TextStyle(
                                color = textCo, textAlign = TextAlign.End
                            ),
                        )
                    }
                }
            }


            Image(
                modifier = Modifier
                    .padding(Dimens.halfDefaultMargin)
                    .weight(1f),
                painter = painterResource(R.drawable.ic_forward_arrow),
                contentDescription = ""
            )

        }
    }
}

