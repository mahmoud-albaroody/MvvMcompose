package com.bitaqaty.reseller.ui.presentation.transactionsScreen


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.domain.TransactionLogUseCase
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val repo: TransactionLogUseCase) :
    ViewModel() {
     val transactionLogs: MutableState<DataState<PairType<TransactionLogResult, Void>>?> =
        mutableStateOf(null)

    val jsonObject: JsonObject = JsonObject()
    fun transactionsLog() {
        viewModelScope.launch {
            repo.invoke(jsonObject).onEach {
                transactionLogs.value = it
            }.launchIn(viewModelScope)
        }
    }
}