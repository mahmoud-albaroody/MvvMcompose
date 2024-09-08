package com.bitaqaty.reseller.ui.presentation.bankTransfer


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestOneCardAccountsBody
import com.bitaqaty.reseller.data.model.SavedAccount
import com.bitaqaty.reseller.data.model.SavedAccounts
import com.bitaqaty.reseller.data.model.SearchBank
import com.bitaqaty.reseller.domain.BankTransferUseCase
import com.bitaqaty.reseller.utilities.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    private val _savedAccounts =
        MutableSharedFlow<SavedAccounts>()
    val savedAccounts: MutableSharedFlow<SavedAccounts>
        get() = _savedAccounts

    private val _senderCountries =
        MutableSharedFlow<AccountsCountries>()
    val senderCountries: MutableSharedFlow<AccountsCountries>
        get() = _senderCountries

    private val _senderAccountsByCountry =
        MutableSharedFlow<AccountsCountries>()
    val senderAccountsByCountry: MutableSharedFlow<AccountsCountries>
        get() = _senderAccountsByCountry

    private val _isSelected = mutableStateOf(false)
    val isSelected: State<Boolean> = _isSelected

    private val _isOtherSelected = mutableStateOf(false)
    val isOtherSelected: State<Boolean> = _isOtherSelected

    private val _accountNum = mutableStateOf("")
    val accountNum: State<String> = _accountNum

    private val _isSaved = mutableStateOf(false)
    val isSaved: State<Boolean> = _isSaved

    private val _selectedAccount = mutableStateOf<SavedAccount?>(null)
    val selectedAccount: State<SavedAccount?> = _selectedAccount

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

    fun getSavedAccounts() {
        viewModelScope.launch {
            val userName = Utils.getUserData()?.reseller?.username
            bankTransferUseCase.getSavedAccounts(RequestOneCardAccountsBody(userName))
                .catch {

                }
                .onEach {
                    _savedAccounts.emit(it)
                    _selectedAccount.value = it.savedAccounts?.find { it.selected }
                    _accountNum.value = _selectedAccount.value?.bankAccountNumber ?: ""
                    _isSaved.value = it.savedAccounts?.any { it.bankAccountNumber == _accountNum.value } == true
                    getSenderAccountsByCountry(_selectedAccount.value?.fromCountryId ?: "")
                }.launchIn(viewModelScope)
        }
    }

    fun getSenderCountries() {
        viewModelScope.launch {
            bankTransferUseCase.senderCounters()
                .catch {

                }
                .onEach {
                    _senderCountries.emit(it)
                    //getSenderAccountsByCountry(it.lookupList?.get(0)?.id.toString())
                }.launchIn(viewModelScope)
        }
    }

    fun getSenderAccountsByCountry(id: String) {
        viewModelScope.launch {
            bankTransferUseCase.senderAccountByCounter(id)
                .catch {

                }
                .onEach {
                    _senderAccountsByCountry.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun isSelected(selected: Boolean){
        _isSelected.value = selected
    }

    fun isOtherSelected(selected: Boolean){
        _isOtherSelected.value = selected
    }

    fun onChangeAccountNum(value: String){
        _accountNum.value = value
        _isSaved.value = selectedAccount.value?.bankAccountNumber == accountNum.value
    }

    fun onChangeSelectedAccount(account: SavedAccount){
        _selectedAccount.value = account
        _accountNum.value = account.bankAccountNumber
    }
}