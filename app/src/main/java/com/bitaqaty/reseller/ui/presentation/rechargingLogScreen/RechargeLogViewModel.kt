package com.bitaqaty.reseller.ui.presentation.rechargingLogScreen


import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.domain.RechargeLogUseCase
import com.bitaqaty.reseller.domain.SettlementUseCase
import com.bitaqaty.reseller.utilities.Globals
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class RechargeLogViewModel @Inject constructor(private val repo: RechargeLogUseCase) :
    ViewModel() {
    val rechargeLogs: MutableState<RechargingLogResult?> =
        mutableStateOf(null)

    fun getRechargingList(
        pageNumber: Int, discriminatorValue: String,
        dateFrom: String, dateTo: String, dateMethod: String?
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("timeZone", TimeZone.getDefault().id)
//        jsonObject.addProperty("customerId", CurrentUser.getInstance()?.reseller?.id)
//        jsonObject.addProperty("pageSize", Globals.PAGE_SIZE)
        jsonObject.addProperty("customerId", 1)
        jsonObject.addProperty("pageSize", 10)
        jsonObject.addProperty("pageNumber", pageNumber)
        if (discriminatorValue.isNotEmpty()) {
            jsonObject.addProperty("discriminatorValue", discriminatorValue)
        }
        if (dateFrom.isNotEmpty()) {
            jsonObject.addProperty("dateFrom", dateFrom)
        }
        if (dateTo.isNotEmpty()) {
            jsonObject.addProperty("dateTo", dateTo)
        }

        if (dateMethod != null) {
            jsonObject.addProperty("searchPeriod", dateMethod)
        }


        viewModelScope.launch {
            repo.invoke(jsonObject)
                .catch {
                }
                .onEach {
                    rechargeLogs.value = it
                }.launchIn(viewModelScope)
        }
    }

}