package com.bitaqaty.reseller.ui.presentation.verificationCode


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.StatusResponse
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.network.Resource
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
        MutableSharedFlow<Resource<ValidateResetAccessData>>()
    val validateResetAccessData: MutableSharedFlow<Resource<ValidateResetAccessData>>
        get() = _validateResetAccessData


    private val _validateCode =
        MutableSharedFlow<Resource<User>>()
    val validateCode: MutableSharedFlow<Resource<User>>
        get() = _validateCode

    private val _remainingTrials =
        MutableSharedFlow<Resource<RemainingTrials>>()
    val remainingTrials: MutableSharedFlow<Resource<RemainingTrials>>
        get() = _remainingTrials
    private val _resendResetAccess =
        MutableSharedFlow<Resource<StatusResponse>>()
    val resendResetAccess: MutableSharedFlow<Resource<StatusResponse>>
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

    fun validateVerificationCode(token: String?, code: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("loginProcessToken", token)
        jsonObject.addProperty("smsVerificationCode", code)
        viewModelScope.launch {
            repo.validateVerificationCode(jsonObject)
                .catch {

                }.buffer().collect {
                    _validateCode.emit(it)
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

                }.buffer().collect {
                    _resendResetAccess.emit(it)
                }
        }
    }
}
