package com.bitaqaty.reseller.ui.presentation.bankTransferList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.SearchBank
import com.bitaqaty.reseller.domain.BankTransferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankTransferListViewModel @Inject constructor(private val bankTransferUseCase: BankTransferUseCase) :
    ViewModel() {
    private val _searchBankTransfer =
        MutableSharedFlow<SearchBank>()
    val searchBankTransfer: MutableSharedFlow<SearchBank>
        get() = _searchBankTransfer


    fun searchBankTransfer(bankTransferLogBody: RequestBankTransferLogBody) {
        viewModelScope.launch {
            bankTransferUseCase.searchBankTransfer(bankTransferLogBody)
                .catch {

                }
                .onEach {
                    _searchBankTransfer.emit(it)
                }.launchIn(viewModelScope)
        }
    }
}