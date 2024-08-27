package com.bitaqaty.reseller.ui.presentation.moreScreen


import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.activity.MainActivity
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey80
import com.bitaqaty.reseller.ui.theme.Red
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.LocaleHelper
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.getLang
import com.bitaqaty.reseller.utilities.Utils.loadLocale
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun MoreScreen(navController: NavController, modifier: Modifier) {
    val moreViewModel: MoreViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {

        moreViewModel.viewModelScope.launch {
            moreViewModel.getLogout.collect {
                Utils.deleteUserData()
                navController.navigate(Screen.MainScreen.route)
            }
        }
    }
    More(onItemClick = {

        when (it) {

            context.resources.getString(R.string.notification) -> {
                navController.navigate(Screen.Notification.route)
            }

            context.resources.getString(R.string.edit_profile) -> {
                navController.navigate(Screen.MyProfileScreen.route)
            }

            context.resources.getString(R.string.reports) -> {
                navController.navigate(Screen.SalesReportScreen.route)
            }

            context.resources.getString(R.string.add_balance) -> {
                navController.navigate(Screen.ChargeBalanceScreen.route)
            }

            context.resources.getString(R.string.reseller_support) -> {
            }

            "English" -> {
                changeLang(context)
            }

            "عربي" -> {
                changeLang(context)
            }

            context.resources.getString(R.string.settlement_transaction) -> {
                navController.navigate(Screen.SettlementTransactionsScreen.route)
            }

            context.resources.getString(R.string.product_discount_list) -> {
                navController.navigate(Screen.ProductsDiscountsScreen.route)
            }

            context.resources.getString(R.string.logout) -> {
                moreViewModel.logout()
            }
        }

    })
}

private fun changeLang(context: Context) {
    var lan = ""
    lan = if (getLang() == "en") {
        "ar"
    } else {
        "en"
    }
    Utils.saveLang(lan)

    (context as? Activity)?.finish()

    (context as? Activity)?.startActivity(
        Intent(
            context,
            MainActivity::class.java
        )
    )
}


@Composable
fun More(onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val langText: String = if (getLang() == "en") {
        "عربي"
    } else {
        "English"
    }

    Column(
        Modifier
            .background(White)
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin)
            .padding(top = Dimens.halfDefaultMargin)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            MoreItem(
                text = stringResource(id = R.string.notification),
                text2 = "3",
                textColor = Blue,
                icon = R.drawable.ic_notification,
                weight = 8f, onItemClick = {
                    onItemClick.invoke(context.getString(R.string.notification))
                }
            )
            MoreReport(
                stringResource(R.string.edit_profile),
                stringResource(R.string.add_balance),
                text3 = Utils.getUserData()?.reseller?.username ?: "",
                text4 = Utils.getUserData()?.reseller?.getBalance() ?: "",
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreReport(
                text3 = stringResource(R.string.reports),
                text4 = stringResource(R.string.reseller_support),
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreReport(
                text3 = stringResource(R.string.product_discount_list),
                text4 = stringResource(R.string.settlement_transaction),
                onItemClick = {
                    onItemClick.invoke(it)
                }
            )
            MoreItem(
                text = stringResource(id = R.string.language),
                text2 = langText,
                textColor = Blue,
                icon = R.drawable.ic_language,
                weight = 8f,
                onItemClick = {
                    if (lang == "ar") {
                        onItemClick.invoke("English")
                    } else {
                        onItemClick.invoke("عربي")
                    }
                }
            )
        }
        Column {
            Box(Modifier.padding(top = Dimens.fourDefaultMargin)) {
                Divider(
                    modifier = Modifier
                        .height(Dimens.DefaultMargin0)
                        .fillMaxWidth()  // Set the thickness of the vertical line
                        .background(LightGrey80)
                )
            }
            Box(Modifier.padding(top = Dimens.halfDefaultMargin)) {
                FilterButton(backgroundTex = Red,
                    text = stringResource(R.string.logout),
                    iconVisibility = false,
                    textColor = White, horizontalPadding = Dimens.DefaultMargin,
                    onApplyFilterClick = {
                        onItemClick.invoke(context.getString(R.string.logout))
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
            .padding(top = Dimens.fourDefaultMargin)
            .clickable {
                onItemClick.invoke()
            },
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = White)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.halfDefaultMargin,
                    vertical = Dimens.fourDefaultMargin
                ),
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
                    var textCo: Color = White
                    var color = Red

                    if (text2 == "English" || text2 == "عربي") {
                        color = Transparent
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

