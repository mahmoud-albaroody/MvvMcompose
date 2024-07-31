package com.bitaqaty.reseller.ui.presentation.transactionsScreen


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.domain.TransactionLogUseCase
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val repo: TransactionLogUseCase) :
    ViewModel() {

    private val _transactionLogs =
        MutableSharedFlow<TransactionLogResult>()
    val transactionLogs: MutableSharedFlow<TransactionLogResult>
        get() = _transactionLogs



    fun transactionsLog() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("pageSize", 20)
        jsonObject.addProperty("pageNumber", 1)
        viewModelScope.launch {
            repo.invoke(jsonObject)
                .catch {
                }
                .onEach {
                    transactionLogs.emit(it)
                }.launchIn(viewModelScope)
        }
    }
}