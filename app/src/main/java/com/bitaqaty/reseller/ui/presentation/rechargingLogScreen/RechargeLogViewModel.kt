package com.bitaqaty.reseller.ui.presentation.rechargingLogScreen


import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.domain.RechargeLogUseCase
import com.bitaqaty.reseller.domain.SettlementUseCase
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class RechargeLogViewModel @Inject constructor(private val repo: RechargeLogUseCase) :
    ViewModel() {

    private val _rechargeLogs =
        MutableSharedFlow<Resource<RechargingLogResult>>()
    val rechargeLogs: MutableSharedFlow<Resource<RechargingLogResult>>
        get() = _rechargeLogs

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
            repo.rechargeLog(jsonObject)
                .catch {
                    _rechargeLogs.emit(
                        Resource.DataError(
                            null,
                            0, null
                        )
                    )
                }
                .onEach {
                    _rechargeLogs.emit(it)
                }.launchIn(viewModelScope)
        }
    }

}