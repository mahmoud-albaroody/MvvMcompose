package com.bitaqaty.reseller.ui.presentation.recharge

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.presentation.profileScreen.ProfileFooter
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Globals.SETTINGS
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.isCashInApp
import com.bitaqaty.reseller.utilities.Utils.isMadaApp
import com.bitaqaty.reseller.utilities.Utils.isNearPayApp
import com.bitaqaty.reseller.utilities.Utils.isPartnerApp


@Composable
fun RechargeScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: RechargeViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    Recharge(onRechargeClick = {
        navController.navigate(Screen.RechargeUsingMadaScreen.route)
    })
}


@Composable
fun Recharge(onRechargeClick: () -> Unit) {
    val type: Int = -1
    var min = ""
    var max = ""
    var minText = ""
    var perRequest = ""
    if (SETTINGS.size > 0) {
        if (type == Globals.RECHARGE_TYPE.CREDIT.value) {
            val minValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.CREDIT_CARD_MINIMUM_AMOUNT_PER_REQUEST.value }
            min = minValue.propertyValue

            val maxValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.CREDIT_CARD_MAXIMUM_AMOUNT_PER_REQUEST.value }
            max = maxValue.propertyValue
        } else if (type == Globals.RECHARGE_TYPE.AMEX.value) {
            val minValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.AMEX_MINIMUM_AMOUNT_PER_REQUEST.value }
            min = minValue.propertyValue
            val maxValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.AMEX_MAXIMUM_AMOUNT_PER_REQUEST.value }
            max = maxValue.propertyValue

        } else if (type == Globals.RECHARGE_TYPE.MADA.value) {
            val minValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.MADA_MINIMUM_AMOUNT_PER_REQUEST.value }
            min = minValue.propertyValue

            val maxValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.MADA_MAXIMUM_AMOUNT_PER_REQUEST.value }
            max = maxValue.propertyValue

        } else if (type == Globals.RECHARGE_TYPE.MADA_AHLY.value && isMadaApp()) {
            val minValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.SUREPAY_MADA_MINIMUM_AMOUNT_PER_REQUEST.value }
            min = minValue.propertyValue

            val maxValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.SUREPAY_MADA_MAXIMUM_AMOUNT_PER_REQUEST.value }
            max = maxValue.propertyValue

            val perRequestValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.SUREPAY_MADA_NUMBER_OF_REQUESTS_PER_DAY.value }
            perRequest = perRequestValue.propertyValue

        } else if (type == Globals.RECHARGE_TYPE.MADA_AHLY.value && (isCashInApp() || isNearPayApp())) {
            val minValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.CACHIN_MADA_MINIMUM_AMOUNT_PER_REQUEST.value }
            min = minValue.propertyValue

            val maxValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.CACHIN_MADA_MAXIMUM_AMOUNT_PER_REQUEST.value }
            max = maxValue.propertyValue

            val perRequestValue =
                SETTINGS.first { s -> s.propertyKey == Globals.SETTING_KEYS.CACHIN_MADA_NUMBER_OF_REQUESTS_PER_DAY.value }
            perRequest = perRequestValue.propertyValue
        }
        minText = if (min == "0") {
            stringResource(R.string.there_is_no_minimum_amount_for_recharge)
        } else {
            stringResource(R.string.minAmount).replace("[X]", min)

        }


    }
    Column(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(Modifier.padding(top = Dimens.padding12)) {
            DynamicSelectTextField(
                TextAlign.Start,
                stringArrayResource(R.array.credit_mada_instruction_arr).toList(), false
            ){

            }
        }
        RechargeAmount()
        Column(Modifier.padding(top = Dimens.DefaultMargin)) {
            ProfileFooter(
                stringResource(id = R.string.maxAmount).replace("[X]", max),
                R.drawable.ic_info_circle
            )
            ProfileFooter(minText, R.drawable.ic_info_circle)
            if (isPartnerApp())
                ProfileFooter(
                    stringResource(id = R.string.max_number_of_recharge_per_day).replace(
                        "[X]",
                        perRequest
                    ),
                    R.drawable.ic_info_circle
                )
        }
        Box(Modifier.padding(top = Dimens.DefaultMargin20)) {
            FilterButton(
                backgroundTex = Blue100, text = stringResource(id = R.string.recharge),
                iconVisibility = false,
                textColor = White,
                horizontalPadding = Dimens.DefaultMargin,
                onApplyFilterClick = {
                    onRechargeClick.invoke()
                }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding30)
                .padding(top = Dimens.halfDefaultMargin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.by_continue_recharging_you_agree_on),
                style = TextStyle(color = LightGrey200, fontSize = 11.sp)
            )
            Text(
                text = stringResource(id = R.string.termsAndConditions),
                style = TextStyle(color = BebeBlue, fontSize = 11.sp)
            )
        }
    }
}

@Preview
@Composable
fun RechargeAmount() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)

    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                )
                .padding(
                    top = Dimens.DefaultMargin20,
                    bottom = Dimens.quarterDefaultMargin
                ),
            text = stringResource(id = R.string.btrr_recharge_amount),
            style = TextStyle(color = LightGrey400, fontSize = 14.sp)
        )
        Box(
            modifier = Modifier.padding(
                bottom = Dimens.padding30
            )
        ) {
            DynamicSelectTextField(
                TextAlign.Center,
                arrayListOf(
                    "100",
                    "200",
                    "500",
                    "1000",
                    "5000",
                ), true
            ){

            }
        }
    }
}

