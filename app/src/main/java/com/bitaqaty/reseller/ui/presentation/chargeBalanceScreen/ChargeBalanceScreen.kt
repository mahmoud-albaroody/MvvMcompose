package com.bitaqaty.reseller.ui.presentation.chargeBalanceScreen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.profileScreen.AccountManager
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.utilities.Utils


@Composable
fun ChargeBalanceScreen(navController: NavController, modifier: Modifier) {
    val chargeBalanceViewModel: ChargeBalanceViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {}
    ChargeBalance(onItemClick = {
        when (it) {
            context.resources.getString(R.string.mada_ahly) -> {
                navController.navigate(Screen.RechargeScreen.route)
            }

            context.resources.getString(R.string.bank_transfer) -> {
                navController.navigate(Screen.BankTransferScreen.route)
            }

            context.resources.getString(R.string.rechargeLog) -> {
                navController.navigate(Screen.RechargeLogScreen.route)
            }

            "list" -> {
                navController.navigate(Screen.BankTransferListScreen.route)
            }
        }
    })
}


@Composable
fun ChargeBalance(onItemClick: (String) -> Unit) {
    val context = LocalContext.current
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
            MadaAhly(stringResource(id = R.string.mada_ahly), BebeBlue, Transparent, onItemClick = {
                onItemClick(context.getString(R.string.mada_ahly))
            })
            MadaAhly(
                stringResource(id = R.string.bank_transfer),
                BebeBlue,
                Transparent,
                onItemClick = {
                    onItemClick(context.getString(R.string.bank_transfer))
                })
        }

        FooterChangeBalance(onItemClick = {
            onItemClick(it)
        })
    }
}

@Preview
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
                            text = stringResource(id = R.string.welcome),
                            style = TextStyle(color = LightGrey200),
                        )
                        Text(
                            text = Utils.getUserData()?.reseller?.username ?: "",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = Dimens.halfDefaultMargin)
                        )
                    }
                }
            }
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.your_balance),
                    style = TextStyle(color = LightGrey200)
                )
                Text(
                    text = Utils.getUserData()?.reseller?.getBalance() ?: "",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = Dimens.halfDefaultMargin)
                )
            }

        }
    }
}


@Composable
fun MadaAhly(
    text: String, textColor: Color,
    cardColor: Color, onItemClick: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()

            .padding(Dimens.halfDefaultMargin),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = cardColor)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick.invoke()
                }
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


@Composable
fun FooterChangeBalance(onItemClick: (String) -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.halfDefaultMargin,
                end = Dimens.halfDefaultMargin
            )
            .padding(bottom = Dimens.halfDefaultMargin)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = Dimens.fourDefaultMargin)
                .clickable {
                    onItemClick(context.getString(R.string.rechargeLog))
                }
        ) {
            AccountManager(
                stringResource(id = R.string.rechargeLog),
                R.drawable.ic_cart_large, 12,
                onItemClick = {
                    onItemClick(context.getString(R.string.rechargeLog))
                })

        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = Dimens.fourDefaultMargin)
                .clickable {
                    onItemClick(context.getString(R.string.bank_transfer))
                }
        ) {
            AccountManager(
                stringResource(id = R.string.bank_transfer),
                R.drawable.ic_calendar_date_schedu,
                12,
                onItemClick = {
                    onItemClick("list")
                })
        }
    }
}