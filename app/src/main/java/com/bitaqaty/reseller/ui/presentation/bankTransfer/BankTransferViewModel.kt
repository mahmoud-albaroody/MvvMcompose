package com.bitaqaty.reseller.ui.presentation.bankTransfer


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestOneCardAccountsBody
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
class BankTransferViewModel @Inject constructor(private val bankTransferUseCase: BankTransferUseCase) :
    ViewModel() {
    private val _getProductLookList =
        MutableSharedFlow<ArrayList<Product>>()
    val getProductLookList: MutableSharedFlow<ArrayList<Product>>
        get() = _getProductLookList

    private val _onecardAccount =
        MutableSharedFlow<AccountsByCountry>()
    val onecardAccount: MutableSharedFlow<AccountsByCountry>
        get() = _onecardAccount


    private val _onecardCountries =
        MutableSharedFlow<AccountsCountries>()
    val onecardCountries: MutableSharedFlow<AccountsCountries>
        get() = _onecardCountries


    private val _searchBankTransfer =
        MutableSharedFlow<SearchBank>()
    val searchBankTransfer: MutableSharedFlow<SearchBank>
        get() = _searchBankTransfer


    fun onecardAccount(requestOneCardAccountsBody: RequestOneCardAccountsBody) {
        viewModelScope.launch {

            bankTransferUseCase.onecardAccount(requestOneCardAccountsBody)
                .catch {

                }
                .onEach {
                    _onecardAccount.emit(it)
                }.launchIn(viewModelScope)
        }
    }
    fun onecardCountries() {
        viewModelScope.launch {
            bankTransferUseCase.onecardCountries()
                .catch {

                }
                .onEach {
                    _onecardCountries.emit(it)
                }.launchIn(viewModelScope)
        }
    }



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