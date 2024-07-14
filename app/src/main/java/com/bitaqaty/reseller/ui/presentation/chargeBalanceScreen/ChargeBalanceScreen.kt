package com.bitaqaty.reseller.ui.presentation.chargeBalanceScreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.bitaqaty.reseller.ui.presentation.profileScreen.AccountManager
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun ChargeBalanceScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: ChargeBalanceViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    ChargeBalance()
}


@Preview
@Composable
fun ChargeBalance() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = Dimens.halfDefaultMargin
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            ProfileBalance()
            MadaAhly("Mada Ahly", LightGrey200)
            MadaAhly("Bank Transfer", BebeBlue)
        }

        FooterChangeBalance()
    }
}

@Composable
fun ProfileBalance() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.halfDefaultMargin),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, LightGrey400),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(Dimens.halfDefaultMargin)
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Image(
                            modifier = Modifier.padding(Dimens.halfDefaultMargin),
                            painter = painterResource(R.drawable.ic_personal_profile),
                            contentDescription = ""
                        )
                    }
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Welcome",
                            style = TextStyle(color = LightGrey200),
                        )
                        Text(
                            text = "Khalid Ali",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = Dimens.halfDefaultMargin)
                        )
                    }
                }
            }
            Column(
                Modifier.weight(1f),
            ) {
                Text(
                    text = "Your Balance",
                    style = TextStyle(color = LightGrey200)
                )
                Text(
                    text = "15000,00",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = Dimens.halfDefaultMargin)
                )
            }

        }
    }
}


@Composable
fun MadaAhly(text: String, textColor: Color) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.halfDefaultMargin),
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
            Image(
                modifier = Modifier
                    .padding(Dimens.halfDefaultMargin),
                painter = painterResource(R.drawable.ic_personal_profile),
                contentDescription = ""
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                text = text,
                style = TextStyle(color = textColor),
            )
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

@Preview
@Composable
fun FooterChangeBalance() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.halfDefaultMargin,
                end = Dimens.halfDefaultMargin
            ).padding(bottom = Dimens.halfDefaultMargin)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = Dimens.fourDefaultMargin)
        ) {
            AccountManager("Recharging Log", R.drawable.ic_cart_large,12)

        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = Dimens.fourDefaultMargin)
        ) {
            AccountManager("Bank Transfer", R.drawable.ic_calendar_date_schedu,12)
        }
    }
}