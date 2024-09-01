package com.bitaqaty.reseller.ui.presentation.productDetails

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.BuildConfig
import com.bitaqaty.reseller.data.model.InitPurchaseResponse
import com.bitaqaty.reseller.data.model.MadaResponse
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductInfo
import com.bitaqaty.reseller.data.model.PurchaseRequest
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.domain.PurchaseUseCase
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.nearpay.sdk.data.models.TransactionReceipt
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repo: BBRepository, val purchaseUseCase: PurchaseUseCase
) : ViewModel() {


    private val _initPurchase =
        MutableSharedFlow<Resource<InitPurchaseResponse>>()
    val initPurchase: MutableSharedFlow<Resource<InitPurchaseResponse>>
        get() = _initPurchase

    private val _completePurchaseCart =
        MutableSharedFlow<Resource<PurchaseResponse>>()
    val completePurchaseCart: MutableSharedFlow<Resource<PurchaseResponse>>
        get() = _completePurchaseCart


    private var _counter = mutableIntStateOf(1)
    val counter: State<Int> = _counter

    private val _alpha = mutableFloatStateOf(1f)
    val alpha: State<Float> = _alpha


    fun initPurchase(cartProducts: ArrayList<Product>, amount: String) {

        val jsonObject = JsonObject()
        val items: ArrayList<ProductInfo> = arrayListOf()
        var timeZone = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timeZone = TimeZone.getDefault().id
        }

        cartProducts.forEach { product ->
            items.add(
                ProductInfo(
                    timeZone,
                    Utils.getUserData()?.reseller?.id!!,
                    product.getProductsId(),
                    product.getPrice(),
                    product.quantity
                )
            )
        }


        jsonObject.addProperty("amount", amount)
        jsonObject.addProperty("chargingMethod", "MADA_AHLI")
        jsonObject.add("purchaseProductRequestDTOList", Gson().toJsonTree(items))

        if (Utils.isMadaApp()) {
            var simCardType: String? = ""
            if (Utils.isMobily()) {
                simCardType = "Mobily"
            }
            val json1 = JsonObject()
            json1.addProperty("madaVersion", Utils.getMadaVersion())
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE)
            json1.addProperty("simCardType", simCardType)
            jsonObject.add("posMachineDetails", json1)
        } else if (Utils.isCashInApp() || Utils.isNearPayApp()) {
            var simCardType: String? = ""
            if (Utils.isMobily()) {
                simCardType = "Mobily"
            }
            val json1 = JsonObject()
            json1.addProperty("madaVersion", "2.1.68")
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE)
            json1.addProperty("simCardType", simCardType)
            jsonObject.add("posMachineDetails", json1)
        }

        viewModelScope.launch {
            _initPurchase.emit(Resource.Loading())
            purchaseUseCase.initPurchase(jsonObject)
                .catch {

                }
                .onEach {
                    _initPurchase.emit(it)
                }.launchIn(viewModelScope)
        }

    }


    fun completePurchase(
        cartProducts: ArrayList<Product>,
        paymentRefNumber: String? = null,
        event: MadaResponse? = null,
        cashInTransaction: TransactionReceipt? = null,
        isCart: Boolean,
        requestStatue: String? = null, isBalance: Boolean
    ) {

        val json = JsonObject()
        var timeZone = ""
        val items: ArrayList<ProductInfo> = arrayListOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timeZone = TimeZone.getDefault().id
        }

        if (cartProducts.isNotEmpty())
            cartProducts.forEach { product ->
                items.add(
                    ProductInfo(
                        timeZone,
                        Utils.getUserData()?.reseller?.id!!,
                        product.getProductsId(),
                        product.getPrice(),
                        product.quantity
                    )
                )
            }
        json.addProperty("type", "")
        json.addProperty("cart", isCart)
        if (items.isNotEmpty())
            json.add("purchaseProductRequestDTOList", Gson().toJsonTree(items))
        if (Utils.isMadaApp() && !isBalance) {
            json.addProperty("paymentRefNumber", paymentRefNumber)
//          json.addProperty("profitType", "MONTHLY")
            json.addProperty("amount", event?.madaResponseModel?.aMOUNT)
            json.addProperty("usedCard", event?.madaResponseModel?.sCHEME_N_E)
            json.addProperty("cardHolderName", event?.madaResponseModel?.cRD_HLDR_N)
            json.addProperty("authorizationCode", event?.madaResponseModel?.aUTH)
            json.addProperty("cardNumber", event?.madaResponseModel?.pAN)
            json.addProperty("responseBody", event?.result)
            json.addProperty("signature", event?.signature)
            json.addProperty("rrn", event?.madaResponseModel?.rRN)

            when (event?.madaResponseModel?.tX_RSLT) {
                "0" -> {
                    json.addProperty("requestStatus", "APPROVED")
                }

                "1" -> {
                    json.addProperty("requestStatus", "DECLINED")
                }

                "2" -> {
                    json.addProperty("requestStatus", "TRANSACTION_VOID")
                }

                "3" -> {
                    json.addProperty("requestStatus", "CANCELLED")
                }
            }
            var simCardType: String? = ""
            if (Utils.isMobily()) {
                simCardType = "Mobily"
            }
            val json1 = JsonObject()
            json1.addProperty("madaVersion", Utils.getMadaVersion())
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE)
            json1.addProperty("simCardType", simCardType)
            json.add("posMachineDetails", json1)


        }
        else if ((Utils.isCashInApp() || Utils.isNearPayApp()) && !isBalance) {
            json.addProperty("paymentRefNumber", paymentRefNumber)
            if (cashInTransaction != null) {
                json.addProperty("amount", cashInTransaction.amount_authorized.value)
                json.addProperty("usedCard", cashInTransaction.card_scheme.name.english)
                json.addProperty("cardHolderName", "")
                json.addProperty("authorizationCode", cashInTransaction.approval_code?.value)
                json.addProperty("cardNumber", cashInTransaction.pan)
                json.addProperty("responseBody", Gson().toJson(cashInTransaction).toString())
                json.addProperty("signature", cashInTransaction.transaction_uuid.toString())
                json.addProperty("rrn", cashInTransaction.retrieval_reference_number)
            }
            json.addProperty(
                "requestStatus",
                requestStatue
            )
            var simCardType: String? = ""
            if (Utils.isMobily()) {
                simCardType = "Mobily"
            }
            val json1 = JsonObject()
            json1.addProperty("madaVersion", "")
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE)
            json1.addProperty("simCardType", simCardType)
            json.add("posMachineDetails", json1)

        }
        viewModelScope.launch {
            purchaseUseCase.completePurchaseCart(json, isBalance = isBalance)
                .catch {

                }
                .onEach {
                    _completePurchaseCart.emit(it)
                }.launchIn(viewModelScope)
        }
    }


    fun onDecrease() {
        if (_counter.intValue > 1) _counter.intValue--
    }

    fun onIncrease() {
        _counter.intValue++
    }

    fun resetCounter() {
        _counter.intValue = 1
    }

    fun toggleOpacity(alpha: Float) {
        _alpha.floatValue = alpha
    }
}