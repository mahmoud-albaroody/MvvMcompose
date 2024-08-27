package com.bitaqaty.reseller.ui.presentation.resetPassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.Red
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.HandleError
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(navController: NavController, modifier: Modifier) {
    val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
    val errors = remember { SnapshotStateList<ErrorMessage>() }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        resetPasswordViewModel.viewModelScope.launch {
            resetPasswordViewModel.resetPassword.collect {
                it.data.let { resetAccessDate ->
                    if (resetAccessDate?.errors?.isNotEmpty() == true) {
                        errors.clear()
                        errors.addAll(it.data?.errors!!)
                        showDialog = true
                    } else {
                        resetAccessDate?.token?.let { it1 ->
                            resetPasswordViewModel.getRemainingTrials(
                                it1
                            )
                        }
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("mobileNumber", resetAccessDate?.mobileNumber)
                        jsonObject.addProperty("token", resetAccessDate?.token)
                        navController.navigate(
                            Screen.VerificationCodeScreen
                                .route.plus(
                                    Screen.VerificationCodeScreen.objectName
                                            + "$jsonObject"
                                )
                        )
                    }
                }

            }
        }

    }
    errors.apply {
        HandleError(this, showDialog, navController, context, onDismissClick = {
            showDialog = false
        })
    }
    ResetPassword(stringResource(id = R.string.to_reset_access_data)) {
        resetPasswordViewModel.resendResetAccess(it)
    }
}

@Composable
fun ResetPassword(
    text: String, text2: String? = null, onResetClick: (email: String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    var borderColor: Color = BebeBlue
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(
                vertical = 50.dp,
                horizontal = 24.dp
            ),
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.DefaultMargin20),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.sign_welcome),
                    style = TextStyle(
                        fontSize = 22.sp, color = FontColor
                    ),
                )
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = text,
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )
                text2?.let {
                    Text(
                        modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
                        text = it,
                        style = TextStyle(
                            fontSize = 13.sp, color = FontColor
                        ),
                    )
                }
            }
            Column {
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.reset_email),
                    style = TextStyle(
                        fontSize = Dimens.fontSize12, color = FontColor
                    ),
                )
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.halfDefaultMargin),
                    shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                    border = BorderStroke(Dimens.DefaultMargin0, borderColor),
                    colors = CardDefaults.cardColors(containerColor = Transparent)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = email,

                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.reset_email),
                                style = TextStyle(color = Gray)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            disabledContainerColor = Transparent,
                            cursorColor = BebeBlue, // Customize the cursor color as needed
                            focusedBorderColor = Transparent,
                            unfocusedBorderColor = Transparent,
                            errorBorderColor = Transparent,
                        ),
                        onValueChange = {
                            email = it
                            borderColor = BebeBlue
                            isValid = it.isNotEmpty()
                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        isError = !isValid,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )
                }
                if (!isValid)
                    Text(
                        modifier = Modifier.padding(
                            start = Dimens.padding16,
                            end = Dimens.padding16,
                            top = Dimens.padding8
                        ),
                        text = stringResource(id = R.string.invalid_username),
                        style = TextStyle(
                            fontSize = Dimens.fontSize12, color = Red
                        ),
                    )
            }
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(BebeBlue),
            onClick = {
                if (email.isNotEmpty() && isValid) {
                    onResetClick(email)
                } else {
                    isValid = false
                }
            }) {
            Text(
                text = stringResource(id = R.string.send),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


    }
}

