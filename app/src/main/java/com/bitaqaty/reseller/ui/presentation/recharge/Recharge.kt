package com.bitaqaty.reseller.ui.presentation.recharge

import android.content.Context
import android.graphics.Bitmap
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.localStorage.ProductModel
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.ValidationSurpayChargeResult
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
import com.bitaqaty.reseller.utilities.SunmiPrintHelper
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.isCashInApp
import com.bitaqaty.reseller.utilities.Utils.isMadaApp
import com.bitaqaty.reseller.utilities.Utils.isNearPayApp
import com.bitaqaty.reseller.utilities.Utils.isPartnerApp
import com.bitaqaty.reseller.utilities.addProductToBag
import com.bitaqaty.reseller.utilities.deleteCartDB
import com.bitaqaty.reseller.utilities.initCashIn
import com.bitaqaty.reseller.utilities.madaConnectionResult
import com.bitaqaty.reseller.utilities.printTransaction
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.nearpay.sdk.NearPay
import io.nearpay.sdk.data.models.TransactionReceipt
import io.nearpay.sdk.utils.enums.PurchaseFailure
import io.nearpay.sdk.utils.enums.TransactionData
import io.nearpay.sdk.utils.listeners.PurchaseListener
import io.nearpay.sdk.utils.toImage
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RechargeScreen(navController: NavController, modifier: Modifier) {
    val rechargeViewModel: RechargeViewModel = hiltViewModel()
    val context = LocalContext.current
    var amount by remember { mutableStateOf("100") }
    var transaction: Bitmap? = null
    val compositeDisposable = CompositeDisposable()
    LaunchedEffect(key1 = true) {
        rechargeViewModel.viewModelScope.launch {
            rechargeViewModel.validatePartnerCharging.collect {
                makeRecharge(
                    it,
                    compositeDisposable,
                    amount,
                    context,
                    onCallRecharge = { jsonRechargeObject ->
                        rechargeViewModel.rechargePartner(jsonRechargeObject)
                    },
                    transactionListener = { transactionBitmap ->
                        transaction = transactionBitmap
                    })
            }
        }
        rechargeViewModel.viewModelScope.launch {
            rechargeViewModel.rechargePartner.collect {
                transaction?.let { it1 ->
                    printBitmap(it1, compositeDisposable, rechargeViewModel) {
                        navController.navigate(Screen.RechargeUsingMadaScreen.route)
                    }
                }
            }
        }
    }
    Recharge(onRechargeClick = {
        rechargeViewModel.validatePartnerCharging("MADA_AHLI", amount.toDouble())
    }, amount = {
        amount = it
    })
}


private fun printBitmap(
    transaction: Bitmap,
    compositeDisposable: CompositeDisposable,
    viewModel: RechargeViewModel,
    onFinishRecharge: () -> Unit
) {
    deleteCartDB(compositeDisposable) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            if (isNearPayApp()) {
                printTransaction(transaction)
            }
        }
        onFinishRecharge()
    }
}


@Composable
fun Recharge(onRechargeClick: () -> Unit, amount: (String) -> Unit) {
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
        Box(Modifier.padding(top = Dimens.padding12, bottom = Dimens.padding12)) {
            DynamicSelectTextField(
                TextAlign.Start,
                stringArrayResource(R.array.credit_mada_instruction_arr).toList(), false
            ) {

            }
        }
        RechargeAmount {
            amount(it)
        }

        Column(Modifier.padding(top = Dimens.DefaultMargin20)) {
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


@Composable
fun RechargeAmount(countries: AccountsCountries? = null, onCounterSelectedClick: (String) -> Unit) {
    var title = ""
    var countriesList = arrayListOf<String>()

    if (countries != null) {
        title = stringResource(id = R.string.btrr_countries)
        countries.lookupList?.forEach {
            countriesList.add(it.getName())
        }
    } else {
        title = stringResource(id = R.string.recharge)
        countriesList = arrayListOf(
            "100",
            "200",
            "500",
            "1000",
            "5000",
        )
    }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
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
            text = title,
            style = TextStyle(color = LightGrey400, fontSize = 14.sp)
        )
        Box(
            modifier = Modifier.padding(
                bottom = Dimens.padding30
            )
        ) {
            if (countriesList.isNotEmpty())
                DynamicSelectTextField(
                    TextAlign.Center,
                    countriesList,
                    true
                ) {
                    onCounterSelectedClick(it)
                }
        }
    }
}

private fun purchaseWithCashIn(
    nearpay: NearPay, amount: Long,
    paymentRefNumber: String,
    context: Context,
    onCallRecharge: (JsonObject) -> Unit,
    transactionListener: (transaction: Bitmap) -> Unit
) {
    val amount: Long = amount // [Required] ammount you want to set .
    val enableReceiptUi = true// [optional] true will enable the ui and false will disable
    val enableReversal = true // it will allow you to enable or disable the reverse button
    val finishTimeOut: Long = 0 // Add the number of seconds
    val enableUiDismiss = true // [optional] it will allow you to control dismissing the UI
    val transactionId = null

    nearpay.purchase(amount, paymentRefNumber, enableReceiptUi,
        enableReversal, finishTimeOut, transactionId, enableUiDismiss, object :
            PurchaseListener {
            override fun onPurchaseApproved(transactionData: TransactionData) {
                transactionData.receipts?.get(0)?.toImage(
                    context,
                    380, 12
                ) {
                    it?.let { it1 -> transactionListener(it1) }
//                    if (versionCode > BuildConfig.VERSION_CODE && (isMadaApp()) && isUpdateMandatory) {
//                        Utils.showConfirmDialog(
//                            activity,
//                            getString(R.string.there_is_new_version_of_Bitaqaty),
//                            R.string.update,
//                            Globals.IconType.None,
//                            object : OnFinishListener {
//                                override fun onFinish() {
//                                    val intent =
//                                        activity?.packageManager?.getLaunchIntentForPackage(
//                                            "com.surepay.surestore"
//                                        )
//                                    startActivity(intent)
//                                }
//                            }, isMandatory = isUpdateMandatory
//                        )
//                    }
//                    else if (versionCode > BuildConfig.VERSION_CODE && (isMadaApp()) && !isUpdateMandatory) {
//                        Utils.showConfirmDialog(
//                            activity,
//                            getString(R.string.there_is_new_version_of_Bitaqaty),
//                            R.string.update,
//                            Globals.IconType.None,
//                            object : OnFinishListener {
//                                override fun onFinish() {
//                                    val intent =
//                                        activity?.packageManager?.getLaunchIntentForPackage(
//                                            "com.surepay.surestore"
//                                        )
//                                    startActivity(intent)
//                                }
//                            },
//                            onCancelListener = object : OnFinishListener {
//                                override fun onFinish() {
//                                    Utils.loadHome(requireActivity())
//                                }
//                            }, isMandatory = isUpdateMandatory
//                        )
//                    } else {
//                        if (isCashInApp()) {
//                            CoroutineScope(Dispatchers.IO).launch {
//                                printerManager.open()
//                                printerManager.setPrintGray(2000)
//                                printerManager.setPrintFont("/system/fonts/simsun.ttc")
//                                val pl: PrintLine = BitmapPrintLine(it, 0)
//                                printerManager.addPrintLine(pl)
//                                printerManager.addPrintLine(TextPrintLine())
//                                printerManager.addPrintLine(TextPrintLine())
//                                printerManager.addPrintLine(TextPrintLine())
//                                printerManager.beginPrint(object :
//                                    POIPrinterManager.IPrinterListener {
//                                    override fun onStart() {
//
//                                    }
//
//                                    override fun onFinish() {
//                                        transactionData.receipts?.get(0).let {
//                                            val jsonObject = JsonObject()
//                                            jsonObject.addProperty(
//                                                "amount",
//                                                it?.amount_authorized?.value
//                                            )
//                                            jsonObject.addProperty(
//                                                "resellerId",
//                                                Utils.getUserData()?.reseller?.id
//                                            )
//                                            jsonObject.addProperty("deviceId", Globals.DEV_ID)
//                                            jsonObject.addProperty(
//                                                "requestStatus",
//                                                it?.status_message?.english?.toUpperCase()
//                                            )
//                                            jsonObject.addProperty(
//                                                "paymentRefNumber",
//                                                paymentRefNumber
//                                            )
//                                            jsonObject.addProperty(
//                                                "responseBody",
//                                                Gson().toJson(it).toString()
//                                            )
//                                            jsonObject.addProperty(
//                                                "signature",
//                                                it?.transaction_uuid.toString()
//                                            )
//                                            jsonObject.addProperty(
//                                                "rrn",
//                                                it?.retrieval_reference_number
//                                            )
//                                            jsonObject.addProperty(
//                                                "usedCard",
//                                                it?.card_scheme?.name?.english
//                                            )
//                                            jsonObject.addProperty("cardNumber", it?.pan)
//                                            jsonObject.addProperty("cardHolderName", "")
//                                            jsonObject.addProperty(
//                                                "authorizationCode",
//                                                it?.approval_code?.value
//                                            )
//                                            viewModel?.surePayCharging(jsonObject)
//                                        }
//                                    }
//
//                                    override fun onError(p0: Int, p1: String?) {
//                                    }
//
//                                })
//                            }
//                        }
                    // else
                    if (isNearPayApp()) {
                        transactionData.receipts?.get(0).let {
                            val jsonObject = JsonObject()
                            jsonObject.addProperty(
                                "amount",
                                it?.amount_authorized?.value
                            )
                            jsonObject.addProperty(
                                "resellerId",
                                Utils.getUserData()?.reseller?.id
                            )
                            jsonObject.addProperty("deviceId", Globals.DEV_ID)
                            jsonObject.addProperty(
                                "requestStatus",
                                it?.status_message?.english?.toUpperCase()
                            )
                            jsonObject.addProperty(
                                "paymentRefNumber",
                                paymentRefNumber
                            )
                            jsonObject.addProperty(
                                "responseBody",
                                Gson().toJson(it).toString()
                            )
                            jsonObject.addProperty(
                                "signature",
                                it?.transaction_uuid.toString()
                            )
                            jsonObject.addProperty(
                                "rrn",
                                it?.retrieval_reference_number
                            )
                            jsonObject.addProperty(
                                "usedCard",
                                it?.card_scheme?.name?.english
                            )
                            jsonObject.addProperty("cardNumber", it?.pan)
                            jsonObject.addProperty("cardHolderName", "")
                            jsonObject.addProperty(
                                "authorizationCode",
                                it?.approval_code?.value
                            )
                            onCallRecharge.invoke(jsonObject)
                        }
                    }

                }
            }

            override fun onPurchaseFailed(purchaseFailure: PurchaseFailure) {
                var statusMessage: String = context.getString(R.string.unsuccessful_recharge)
                var transactionReceipt: TransactionReceipt? = null
                val jsonObject = JsonObject()

                jsonObject.addProperty(
                    "resellerId",
                    Utils.getUserData()?.reseller?.id
                )
                jsonObject.addProperty("deviceId", Globals.DEV_ID)

                jsonObject.addProperty(
                    "paymentRefNumber",
                    paymentRefNumber
                )

                when (purchaseFailure) {
                    is PurchaseFailure.PurchaseDeclined -> {
                        // when the payment declined.
                        // Your Code Here
                        transactionReceipt = purchaseFailure.transactionData.receipts?.get(0)
                        statusMessage =
                            transactionReceipt?.status_message?.english.toString() + "\n" +
                                    transactionReceipt?.status_message?.arabic.toString()
                        transactionReceipt.let {
                            val jsonObject = JsonObject()
                            jsonObject.addProperty(
                                "amount",
                                it?.amount_authorized?.value
                            )
                            jsonObject.addProperty(
                                "resellerId",
                                Utils.getUserData()?.reseller?.id
                            )
                            jsonObject.addProperty("deviceId", Globals.DEV_ID)
                            jsonObject.addProperty(
                                "requestStatus",
                                statusMessage
                            )
                            jsonObject.addProperty(
                                "paymentRefNumber",
                                paymentRefNumber
                            )
                            jsonObject.addProperty(
                                "responseBody",
                                Gson().toJson(it).toString()
                            )
                            jsonObject.addProperty(
                                "signature",
                                it?.transaction_uuid.toString()
                            )
                            jsonObject.addProperty(
                                "rrn",
                                it?.retrieval_reference_number
                            )
                            jsonObject.addProperty(
                                "usedCard",
                                it?.card_scheme?.name?.english
                            )
                            jsonObject.addProperty("cardNumber", it?.pan)
                            jsonObject.addProperty("cardHolderName", "")
                            jsonObject.addProperty(
                                "authorizationCode",
                                it?.approval_code?.value
                            )
                        }

                    }

                    is PurchaseFailure.PurchaseRejected -> {
                        // when the payment is rejected .
                        // Your Code Here
                        statusMessage = purchaseFailure.message
                        Log.e("ssss", statusMessage.toString())

                    }

                    is PurchaseFailure.AuthenticationFailed -> {
                        // when the authentication failed .
                        // You can use the following method to update your JWT
                        // Your Code Here
                        statusMessage = purchaseFailure.message

                    }

                    is PurchaseFailure.InvalidStatus -> {
                        // Please note that you can get the status using purchaseFailure.status
                        // Your Code Here
                        statusMessage = purchaseFailure.status[0].name

                    }

                    is PurchaseFailure.GeneralFailure -> {
                        // when there is General error .
                        statusMessage = context.getString(R.string.unsuccessful_recharge)
                    }

                    else -> {}
                }
                jsonObject.addProperty(
                    "requestStatus",
                    statusMessage
                )
                onCallRecharge.invoke(jsonObject)
            }
        })
}

private fun makeRecharge(
    it: ValidationSurpayChargeResult, compositeDisposable: CompositeDisposable, amount: String,
    context: Context,
    onCallRecharge: (JsonObject) -> Unit, transactionListener: (transaction: Bitmap) -> Unit
) {
    var paymentRefNumber = ""
    if (it.chargeValid) {
        paymentRefNumber = it.payment_ref_number
        addProductToBag(
            ProductModel(
                1, paymentRefNumber, "RECHARGE", false, ""
            ), compositeDisposable
        ) {
            if (isMadaApp()) {
                context.madaConnectionResult(
                    totalAmount = ((amount.fmt()).toDouble() * 10).toString(),
                    refName = paymentRefNumber
                )
            } else if (isCashInApp() || isNearPayApp()) {
                if (amount.substringAfter(".").length == 2) {
                    purchaseWithCashIn(
                        context.initCashIn(),
                        (((amount.toInt() * 100).fmt()).toDouble()).toLong(),
                        paymentRefNumber, context, onCallRecharge = {
                            onCallRecharge(it)
                        }, transactionListener = {
                            transactionListener(it)
                        })
                } else {
                    purchaseWithCashIn(
                        context.initCashIn(),
                        (((amount.toInt() * 100).fmt()).toDouble()).toLong(),
                        paymentRefNumber, context, onCallRecharge = {
                            onCallRecharge(it)
                        }, transactionListener = {
                            transactionListener(it)
                        }
                    )
                }
            }

        }
    }
}


