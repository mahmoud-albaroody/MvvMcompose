package com.bitaqaty.reseller.ui.presentation.forgetPassword


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.ForgetPassword
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _resetPassword =
        MutableSharedFlow<ForgetPassword>()
    val resetPassword: MutableSharedFlow<ForgetPassword>
        get() = _resetPassword


    fun forgetPassword(email: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email)
        viewModelScope.launch {
            repo.forgetPassword(jsonObject)
                .catch {
                }.buffer().collect {
                    _resetPassword.emit(it)
                }
        }
    }
}