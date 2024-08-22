package com.bitaqaty.reseller.ui.presentation.verificationCode


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.bitaqaty.reseller.utilities.Utils
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerificationCodeViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    var timer: MutableLiveData<String> = MutableLiveData()

    private val _validateResetAccessData =
        MutableSharedFlow<ValidateResetAccessData>()
    val validateResetAccessData: MutableSharedFlow<ValidateResetAccessData>
        get() = _validateResetAccessData

    private val _remainingTrials =
        MutableSharedFlow<RemainingTrials>()
    val remainingTrials: MutableSharedFlow<RemainingTrials>
        get() = _remainingTrials
    private val _resendResetAccess =
        MutableSharedFlow<String>()
    val resendResetAccess: MutableSharedFlow<String>
        get() = _resendResetAccess

    fun validateResetVerificationCode(token: String?, code: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("token", token)
        jsonObject.addProperty("verificationCode", code)
        viewModelScope.launch {
            repo.validateResetVerificationCode(jsonObject)
                .catch {
                }.buffer().collect {
                    _validateResetAccessData.emit(it)
                }
        }
    }

    fun getRemainingTrials(resetPasswordToken: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("resetAccessDataToken", resetPasswordToken)
        viewModelScope.launch {
            repo.getVerificationRemainingTrials(jsonObject)
                .catch {
                }.buffer().collect {
                    _remainingTrials.emit(it)
                }
        }
    }

    fun resendResetAccessDataSms(resetPasswordToken: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("token", resetPasswordToken)
        viewModelScope.launch {
            repo.resendResetAccessDataSms(jsonObject)
                .catch {
                    Log.e("dddd",it.toString())
                    _resendResetAccess.emit("")
                }.buffer().collect {
                }
        }
    }
}
