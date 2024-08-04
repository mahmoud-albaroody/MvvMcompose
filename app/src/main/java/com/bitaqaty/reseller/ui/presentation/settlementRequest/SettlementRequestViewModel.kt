package com.bitaqaty.reseller.ui.presentation.settlementRequest

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettlementRequestViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel(){
    private val _systemSettingsState =
        mutableStateOf<DataState<ArrayList<SystemSettings>>?>(null)
    val systemSettingsState: State<DataState<ArrayList<SystemSettings>>?> = _systemSettingsState

    private val _personalBankData =
        mutableStateOf<DataState<PersonalBankData>>(DataState.Loading)
    val personalBankData: State<DataState<PersonalBankData>> = _personalBankData

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

    fun getPersonalBankData(){
        viewModelScope.launch {
            //val resellerId = 312717
            repo.getSettlementRequestData().collect{ state ->
                _personalBankData.value = state
            }
        }
    }

    fun validateSettlementRequest(text: String, validationType: ValidationType): Validation{
        return when{
            validationType != ValidationType.NOTES && text.isEmpty() -> Validation(true, "*This field is required")
            (validationType == ValidationType.TRANSFER_AMOUNT && validateTransferAmount(text).isError) -> Validation(true, validateTransferAmount(text).errorMessage)
            (validationType == ValidationType.IBAN && validateIBAN(text).isError) -> Validation(true, validateIBAN(text).errorMessage)
            else -> Validation(false)
        }
    }

    private fun validateTransferAmount(amount: String): Validation {
        return when {
            amount.contains(",") || amount.contains(" ") -> {
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
}