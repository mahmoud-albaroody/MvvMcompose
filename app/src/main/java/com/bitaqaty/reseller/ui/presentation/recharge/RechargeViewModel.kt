package com.bitaqaty.reseller.ui.presentation.recharge


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.BuildConfig
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.PaymentStatus
import com.bitaqaty.reseller.data.model.ValidationSurpayChargeResult
import com.bitaqaty.reseller.domain.RechargeUseCase
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RechargeViewModel @Inject constructor(private val rechargeUseCase: RechargeUseCase) :
    ViewModel() {

    private val _validatePartnerCharging =
        MutableSharedFlow<ValidationSurpayChargeResult>()
    val validatePartnerCharging: MutableSharedFlow<ValidationSurpayChargeResult>
        get() = _validatePartnerCharging

    private val _rechargePartner =
        MutableSharedFlow<Resource<PaymentStatus>>()
    val rechargePartner: MutableSharedFlow<Resource<PaymentStatus>>
        get() = _rechargePartner

    fun validatePartnerCharging(
        paymentMethod: String,
        amount: Double
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("paymentMethod", paymentMethod)
        jsonObject.addProperty("amount", amount)
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
            rechargeUseCase.validateSurePayCharging(jsonObject)
                .catch {

                }
                .onEach {
                    _validatePartnerCharging.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun rechargePartner(jsonObject: JsonObject) {
        viewModelScope.launch {
            rechargeUseCase.partnerRecharge(jsonObject)
                .catch {

                }
                .onEach {
                    _rechargePartner.emit(it)
                }.launchIn(viewModelScope)
        }
    }
}
