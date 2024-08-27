package com.bitaqaty.reseller.ui.presentation.settlementRequest

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.SettlementRequestDataRequest
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettlementRequestViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel(){
    val settlementRequestUiState = MutableStateFlow(SettlementRequestUiState())

    private val _settlementRequestState =
        mutableStateOf<DataState<SettlementRequestResult>?>(null)
    val settlementRequestState: State<DataState<SettlementRequestResult>?> = _settlementRequestState

    private val _systemSettingsState =
        mutableStateOf<DataState<ArrayList<SystemSettings>>?>(null)
    val systemSettingsState: State<DataState<ArrayList<SystemSettings>>?> = _systemSettingsState

    private val _personalBankData =
        mutableStateOf<DataState<PersonalBankData>>(DataState.Loading)
    val personalBankData: State<DataState<PersonalBankData>> = _personalBankData

    private val _user =
        mutableStateOf<DataState<User>>(DataState.Loading)
    val user: State<DataState<User>> = _user

    var balance = "0"

    var minTransferAmount = "0"

    private val _text = mutableStateOf("")
    val text: State<String> = _text

    fun getSystemSettings(){
        viewModelScope.launch {
            repo.getSystemSettings().collect { state ->
                _systemSettingsState.value = state
                if(state is DataState.Success){
                    minTransferAmount = state.data
                        .find { it.propertyKey == "MINIMUM_SETTLEMENT_REQUEST" }?.propertyValue ?: "0"
                }
            }
        }
    }

    fun getUser(){
        viewModelScope.launch {
            repo.getProfile().collect{ state ->
                _user.value = state
                if(state is DataState.Success){
                    balance = "${state.data.reseller?.getBalance()}${state.data.reseller?.getCurrentCurrency()}"
                }
            }
        }
    }

    fun getPersonalBankData(){
        viewModelScope.launch {
            repo.getSettlementRequestData().collect{ state ->
                _personalBankData.value = state
                if(state is DataState.Success){
                    settlementRequestUiState.update { it.copy(
                        amount = state.data.amount.toString(),
                        crNum = state.data.crNumber ?: "",
                        companyName = state.data.companyName ?: "",
                        swiftCode = state.data.swiftCode ?: "",
                        bankName = state.data.bankName ?: "",
                        IBAN = state.data.iban ?: "",
                        notes = state.data.notes ?: "",
                        accountNumber = state.data.accountNumber ?: "",
                        branchAddress = state.data.branchAddress ?: ""
                    ) }
                }
            }
        }
    }

    fun validate(text: String, validationType: ValidationType): Validation{
        when(validationType){
            ValidationType.TRANSFER_AMOUNT -> {
                return if(validateTransferAmount(text).isError){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(amountError = true)) }
                    Validation(true, validateTransferAmount(text).errorMessage)
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(amountError = false)) }
                    Validation(false)
                }
            }

            ValidationType.CR_NUM -> {
                return if(text.isEmpty()){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(crNumError = true)) }
                    Validation(true, "*This field is required")
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(crNumError = false)) }
                    Validation(false)
                }
            }

            ValidationType.COMPANY_NAME -> {
                return if(text.isEmpty()){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(companyNameError = true)) }
                    Validation(true, "*This field is required")
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(companyNameError = false)) }
                    Validation(false)
                }
            }

            ValidationType.SWIFT_CODE -> {
                return if(text.isEmpty()){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(swiftCodeError = true)) }
                    Validation(true, "*This field is required")
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(swiftCodeError = false)) }
                    Validation(false)
                }
            }

            ValidationType.BANK_NAME -> {
                return if(text.isEmpty()){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(bankNameError = true)) }
                    Validation(true, "*This field is required")
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(bankNameError = false)) }
                    Validation(false)
                }
            }

            ValidationType.IBAN -> {
                return if(validateIBAN(text).isError){
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(IBANError = true)) }
                    Validation(true, validateIBAN(text).errorMessage)
                }else{
                    settlementRequestUiState.update { it.copy(errors = it.errors.copy(IBANError = false)) }
                    Validation(false)
                }
            }
            ValidationType.NOTES -> return Validation(false)
        }
    }

    private fun validateTransferAmount(amount: String): Validation {
        return when {
            amount.isEmpty() -> Validation(true, "*This field is required")
            amount.contains(",") || amount.contains(" ") || amount.count{it == '.'} >= 2 -> {
                Validation(true, "*Only numbers are allowed")
            }
            amount.contains("-") -> {
                Validation(true, "*Negative number not accepted")
            }
            amount != "." && amount.toFloat() < minTransferAmount.toFloat() -> {
                Validation(true, "*The Minimum amount to request is $minTransferAmount")
            }
            else -> {
                Validation(false)
            }
        }
    }

    fun validateStartWithZeroOrDecimalPoint(text: String){
        val updatedText = when{
            text.startsWith(".") -> "0."
            (text.startsWith("0") && text.length > 1 && text[1] != '.') -> "0.${text.substring(1)}"
            else -> text
        }
        _text.value = updatedText
    }

    private fun validateIBAN(IBAN: String): Validation {
        return when {
            IBAN.isEmpty() -> Validation(true, "*This field is required")
            IBAN.length >= 2 && IBAN.substring(0,2) != "SA" -> {
                Validation(true, "*IBAN code should started with SA")
            }
            IBAN.trim().length != 24 -> {
                Validation(true, "*Invalid IBAN code")
            }
            IBAN.length == 24 && IBAN.trim().substring(2,24).any { !it.isDigit() } -> {
                Validation(true, "*22 number must be entered")
            }
            else -> {
                Validation(false)
            }
        }
    }

    fun sendSettlementRequest(){
        viewModelScope.launch {
            val settlementRequest = SettlementRequestDataRequest(
                amount = settlementRequestUiState.value.amount,
                crNumber = settlementRequestUiState.value.crNum,
                companyName = settlementRequestUiState.value.companyName,
                swiftCode = settlementRequestUiState.value.swiftCode,
                bankName = settlementRequestUiState.value.bankName,
                iban = settlementRequestUiState.value.IBAN,
                notes = settlementRequestUiState.value.notes,
                accountNumber = settlementRequestUiState.value.accountNumber,
                branchAddress = settlementRequestUiState.value.branchAddress
            )
            repo.createSettlementRequest(settlementRequest).collect { state ->
                _settlementRequestState.value = state
            }
        }
    }
}