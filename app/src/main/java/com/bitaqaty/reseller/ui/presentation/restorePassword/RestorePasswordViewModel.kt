package com.bitaqaty.reseller.ui.presentation.restorePassword


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.User
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
class RestorePasswordViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _resetPassword =
        MutableSharedFlow<ForgetPasswordSend>()
    val resetPassword: MutableSharedFlow<ForgetPasswordSend>
        get() = _resetPassword

    private val _remainingTrials =
        MutableSharedFlow<Resource<RemainingTrials>>()
    val remainingTrials: MutableSharedFlow<Resource<RemainingTrials>>
        get() = _remainingTrials

    fun forgetPasswordSend(email: String?, typeOfMethod: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email)
        jsonObject.addProperty("verificationType", typeOfMethod)
        viewModelScope.launch {
            repo.forgetPasswordSend(jsonObject)
                .catch {
                }.buffer().collect {
                    _resetPassword.emit(it)
                }
        }
    }

    fun getRemainingTrials(resetPasswordToken: String) {
        val jsonObject = JsonObject()
        if (Utils.getResetAccessDataToken().isNullOrEmpty()) {
            jsonObject.addProperty("resetPasswordToken", resetPasswordToken)
        } else {
            jsonObject.addProperty("resetAccessDataToken", resetPasswordToken)
        }
        viewModelScope.launch {
            repo.getVerificationRemainingTrials(jsonObject)
                .catch {
                }.buffer().collect {
                    _remainingTrials.emit(it)
                }
        }
    }
}
