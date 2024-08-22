package com.bitaqaty.reseller.ui.presentation.forgetPasswordCod

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.RemainingTrials
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
class CodeForgetPasswordViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {

    private val _forgetPasswordSend =
        MutableSharedFlow<ForgetPasswordSend>()
    val forgetPasswordSend: MutableSharedFlow<ForgetPasswordSend>
        get() = _forgetPasswordSend

    private val _verifyForgetPassword =
        MutableSharedFlow<DataResult>()
    val verifyForgetPassword: MutableSharedFlow<DataResult>
        get() = _verifyForgetPassword


    private val _remainingTrials =
        MutableSharedFlow<RemainingTrials>()
    val remainingTrials: MutableSharedFlow<RemainingTrials>
        get() = _remainingTrials

    fun forgetPasswordSend(email: String?, typeOfMethod: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email)
        jsonObject.addProperty("verificationType", typeOfMethod)
        viewModelScope.launch {
            repo.forgetPasswordSend(jsonObject)
                .catch {
                }.buffer().collect {
                    _forgetPasswordSend.emit(it)
                }
        }
    }

    fun verifyForgetPassword(
        newPassword: String?,
        confirmNewPassword: String?,
        oldPass: String?,
        resetPasswordToken: String?
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("newPassword", newPassword)
        jsonObject.addProperty("confirmNewPassword", confirmNewPassword)
        jsonObject.addProperty("smsVerificationCode", oldPass)
        jsonObject.addProperty("resetPasswordToken", resetPasswordToken)

        viewModelScope.launch {
            repo.verifyForgetPassword(jsonObject)
                .catch {
                }.buffer().collect {
                    _verifyForgetPassword.emit(it)
                }
        }
    }


    fun getRemainingTrials(resetPasswordToken: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("resetPasswordToken", resetPasswordToken)
        viewModelScope.launch {
            repo.getVerificationRemainingTrials(jsonObject)
                .catch {
                }.buffer().collect {
                    _remainingTrials.emit(it)
                }
        }
    }
}


