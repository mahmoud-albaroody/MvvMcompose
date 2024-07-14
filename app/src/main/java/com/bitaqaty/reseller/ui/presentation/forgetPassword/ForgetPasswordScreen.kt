package com.bitaqaty.reseller.ui.presentation.forgetPassword


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.presentation.resetPassword.ResetPassword

@Composable
fun ForgetPasswordScreen(navController: NavController, modifier: Modifier) {
    val resetPasswordViewModel: ForgetPasswordViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    ResetPassword(
        "To restore your forgotten password.\n" +
                "Please enter your email address, and click send.\n" +
                "An email message containing a link to reset your\n" +
                "password will be sent your email.",
        "Please note that link is valid within one hour"
    )
}



