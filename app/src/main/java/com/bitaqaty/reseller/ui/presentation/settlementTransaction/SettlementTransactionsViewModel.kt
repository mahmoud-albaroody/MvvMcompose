package com.bitaqaty.reseller.ui.presentation.settlementTransaction


import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.domain.SettlementUseCase
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class SettlementTransactionsViewModel @Inject constructor(private val repo: SettlementUseCase) :
    ViewModel() {
    val settlementLogs: MutableState<SettlementResponse?> =
        mutableStateOf(null)

    fun settlementLog(resellerId: Int, pageNumber: Int) {
        val jsonObject = JsonObject()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jsonObject.addProperty("timeZone", TimeZone.getDefault().id)
        }
        jsonObject.addProperty("resellerId", resellerId)
        jsonObject.addProperty("pageSize", 10)
        jsonObject.addProperty("pageNumber", pageNumber)
        viewModelScope.launch {
            repo.invoke(jsonObject)
                .catch {
                }
                .onEach {
                    settlementLogs.value = it
                }.launchIn(viewModelScope)
        }
    }

}