package com.bitaqaty.reseller.ui.presentation.forgetPassword


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.resetPassword.ResetPassword
import com.bitaqaty.reseller.utilities.extention.toJson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

@Composable
fun ForgetPasswordScreen(navController: NavController, modifier: Modifier) {
    val forgetPasswordViewModel: ForgetPasswordViewModel = hiltViewModel()
    var email by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        forgetPasswordViewModel.viewModelScope.launch {
            forgetPasswordViewModel.resetPassword.collect {
                val jsonObject = JsonObject()
                jsonObject.addProperty("email", email)
                jsonObject.addProperty("mobileNumber", it.mobileNumber)
                navController.navigate(
                    Screen.RestorePasswordScreen
                        .route.plus(Screen.RestorePasswordScreen.objectName + "$jsonObject")
                )
            }
        }
    }
    ResetPassword(
        stringResource(id = R.string.reset_instruction)
    ) {
        email = it
        forgetPasswordViewModel.forgetPassword(it)
    }
}



