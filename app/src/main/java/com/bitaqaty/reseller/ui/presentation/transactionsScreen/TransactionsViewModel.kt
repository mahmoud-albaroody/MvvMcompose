package com.bitaqaty.reseller.ui.presentation.transactionsScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.domain.TransactionLogUseCase
import com.bitaqaty.reseller.utilities.network.Resource
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
        MutableSharedFlow<Resource<TransactionLogResult>>()
    val transactionLogs: MutableSharedFlow<Resource<TransactionLogResult>>
        get() = _transactionLogs


    fun transactionsLog(transactionRequestBody: TransactionRequestBody) {
        viewModelScope.launch {
            repo.transactionLog(transactionRequestBody)
                .catch {
                    _transactionLogs.emit(
                        Resource.DataError(
                            null,
                            0, null
                        )
                    )
                }
                .onEach {
                    _transactionLogs.emit(it)
                }.launchIn(viewModelScope)
        }
    }
}