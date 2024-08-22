package com.bitaqaty.reseller.ui.presentation.resetPassword


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.User
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
class ResetPasswordViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _resetPassword =
        MutableSharedFlow<ResetAccessData>()
    val resetPassword: MutableSharedFlow<ResetAccessData>
        get() = _resetPassword

    private val _remainingTrials =
        MutableSharedFlow<RemainingTrials>()
    val remainingTrials: MutableSharedFlow<RemainingTrials>
        get() = _remainingTrials


    fun resendResetAccess(email: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email)
        viewModelScope.launch {
            repo.resetAccessData(jsonObject)
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
