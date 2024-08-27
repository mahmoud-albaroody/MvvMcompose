package com.bitaqaty.reseller.ui.presentation.login


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.BuildConfig
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.bitaqaty.reseller.utilities.Errors
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.isMobily
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _signInState =
        MutableSharedFlow<Resource<DataResult>>()
    val signInState: MutableSharedFlow<Resource<DataResult>>
        get() = _signInState

    private val _authenticatedLoginState =
        MutableSharedFlow<Resource<User>>()
    val authenticatedLoginState: MutableSharedFlow<Resource<User>>
        get() = _authenticatedLoginState

    fun signIn(
        userName: String,
        password: String,
        tid: String? = null,
        termType: String? = null,
        hwSerial: String? = null,
        mnc: String? = null,
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", userName)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("tid", tid)
        jsonObject.addProperty("termType", termType)
        jsonObject.addProperty("hwSerial", hwSerial)
        jsonObject.addProperty("mnc", mnc)
        var simCardType: String? = ""
        if (isMobily()) {
            simCardType = "Mobily"
        }
        val json1 = JsonObject()
        if (Utils.isMadaApp()) {
            json1.addProperty("madaVersion", Utils.getMadaVersion())
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE.toString())
            json1.addProperty("simCardType", simCardType)
        } else if (Utils.isCashInApp()) {
            json1.addProperty("madaVersion", "2.1.72")
            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE.toString())
            json1.addProperty("simCardType", simCardType)
        }
//        else if (Utils.isCashInApp()) {
//            json1.addProperty("madaVersion", "2.1.72")
//            json1.addProperty("bitaqatyBusinessVersion", BuildConfig.VERSION_CODE.toString())
//            json1.addProperty("simCardType", simCardType)
//        }
        jsonObject.add("posMachineDetails", json1)
        viewModelScope.launch {
            repo.signIn(jsonObject)
                .catch {
                }
                .onEach {
                    _signInState.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun authenticatedLogin(loginProcessToken: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("loginProcessToken", loginProcessToken)
        viewModelScope.launch {
            repo.authenticatedLogin(jsonObject)
                .catch {

                }.buffer().collect {
                    _authenticatedLoginState.emit(it)
                }

        }
    }
}