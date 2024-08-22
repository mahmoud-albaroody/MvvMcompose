package com.bitaqaty.reseller.ui.presentation.changePassword


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
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
class ChangePasswordScreenViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _changePassword =
        MutableSharedFlow<DataResult>()
    val changePassword: MutableSharedFlow<DataResult>
        get() = _changePassword


    fun changePassword(newPass: String, confPass: String, oldPass: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("newPassword", newPass)
        jsonObject.addProperty("confirmNewPassword", confPass)
        jsonObject.addProperty("password", oldPass)

        viewModelScope.launch {
            repo.changePassword(jsonObject)
                .catch {
                }.buffer().collect {
                    _changePassword.emit(it)
                }
        }
    }


}
