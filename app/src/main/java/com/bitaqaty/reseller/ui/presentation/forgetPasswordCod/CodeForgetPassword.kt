package com.bitaqaty.reseller.ui.presentation.forgetPasswordCod

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.ValidatePassword
import com.bitaqaty.reseller.ui.presentation.changePassword.ChangePassHint
import com.bitaqaty.reseller.ui.presentation.changePassword.ChangePassword
import com.bitaqaty.reseller.ui.presentation.changePassword.scrollEnabled
import com.bitaqaty.reseller.ui.presentation.verificationCode.VerificationCodeViewModel
import com.bitaqaty.reseller.ui.presentation.verificationCode.updateWaitTime
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.localized
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CodeForgetPasswordScreen(
    navController: NavController, modifier: Modifier, jsonString: String
) {
    val codeForgetPasswordViewModel: CodeForgetPasswordViewModel = hiltViewModel()
    val verificationCodeViewModel: VerificationCodeViewModel = hiltViewModel()

    val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
    val username = jsonObject.get("username").asString
    val verificationType = jsonObject.get("verificationType").asString


    val context = LocalContext.current
    var showTimer by remember { mutableStateOf(false) }
    var timeOutTxt by remember { mutableStateOf("") }
    var remainingTrialsNo by remember { mutableStateOf("") }
    var speed by remember { mutableFloatStateOf(0f) }
    var code by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    val validatePass = remember { mutableStateListOf<ValidatePassword>() }
    val isValidate by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = null) {

        codeForgetPasswordViewModel.forgetPasswordSend(username, verificationType)
        validatePass.add(
            ValidatePassword(
                context.getString(R.string.pass_validation_1),
                isValidate
            )
        )
        validatePass.add(
            ValidatePassword(
                context.getString(R.string.pass_validation_2),
                isValidate
            )
        )
        validatePass.add(
            ValidatePassword(
                context.getString(R.string.pass_validation_3),
                isValidate
            )
        )
        validatePass.add(
            ValidatePassword(
                context.getString(R.string.pass_validation_4),
                isValidate
            )
        )
        validatePass.add(
            ValidatePassword(
                context.getString(R.string.pass_validation_5),
                isValidate
            )
        )
        codeForgetPasswordViewModel.viewModelScope.launch {
            codeForgetPasswordViewModel.forgetPasswordSend.collect {
                it.resetPasswordToken?.let { it1 ->
                    token = it1
                    codeForgetPasswordViewModel.getRemainingTrials(
                        it1
                    )
                }
            }
        }
        codeForgetPasswordViewModel.viewModelScope.launch {
            codeForgetPasswordViewModel.verifyForgetPassword.collect {
                Log.e("ddd", "kkk")
            }
        }
        codeForgetPasswordViewModel.viewModelScope.launch {
            codeForgetPasswordViewModel.remainingTrials.collect { remainingTrials ->
                if (remainingTrials.getResendSmsTrials() >= remainingTrials.getRemainingResendSmsTrial() && remainingTrials.getRemainingResendSmsTrial() != 0) {
                    val trialNo =
                        remainingTrials.getResendSmsTrials() - remainingTrials.getRemainingResendSmsTrial()
                    remainingTrialsNo = "$trialNo / ${remainingTrials.getResendSmsTrials()} ${
                        context.getString(
                            R.string.verification_trial
                        )
                    }".localized()
                    var timeOut = remainingTrials.getWaitingSecondsToResendSms().toFloat()
                    timeOutTxt = " ${timeOut.fmt()} "
                    speed = 1 / remainingTrials.getWaitingSecondsToResendSms().toFloat()
                    showTimer = true
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(2000)
                        while (timeOut > 0) {
                            timeOut -= 1f
                            delay(1000)
                            timeOutTxt = " ${timeOut.fmt()} "
                            if (timeOut <= 0) {
                                showTimer = false
                            }
                        }
                    }
                    if (remainingTrials.getResendSmsTrials() == remainingTrials.getRemainingResendSmsTrial()) {
                        showTimer = false
                    }
                } else {
                    updateWaitTime(
                        context, remainingTrials.getWaitTime(),
                        verificationCodeViewModel
                    )
                    verificationCodeViewModel.timer.value?.let {
                        if (it == "-1") {
                            showTimer = false
                        } else {
                            timeOutTxt = it
                        }
                    }

                }
            }
        }

    }


    Column(
        Modifier
            .fillMaxSize()
    ) {
        CodeForgetPassword(
            validatePass,
            false,
            showTimer,
            speed,
            timeOutTxt,
            remainingTrialsNo,
            onChangePasswordClick = { newPassword, confirmNewPassword, _ ->
                codeForgetPasswordViewModel.verifyForgetPassword(
                    newPassword,
                    confirmNewPassword,
                    code,
                    token
                )
            }, onResendClick = {
                codeForgetPasswordViewModel.forgetPasswordSend(
                    username,
                    verificationType
                )
            }, onCode = {
                code = it
            })
    }
}


@Composable
fun ForgetPassword(
    showTimer: Boolean,
    speed: Float,
    timeOutTxt: String,
    remainingTrialsNo: String,
    onResendClick: () -> Unit,
    onCode: (String) -> Unit,
) {
    var code by rememberSaveable { mutableStateOf("") }
    var isNotValid by remember { mutableStateOf(false) }
    //  val showTimer by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var borderColor: Color = BebeBlue
    val isPlaying by remember {
        mutableStateOf(true)
    }


    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.resend_timer)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false

    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = SimpleColorFilter(BebeBlue.toArgb()),
            keyPath = arrayOf("**")
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(
                horizontal = Dimens.DefaultMargin20
            )
            .padding(top = 50.dp),
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                    text = stringResource(id = R.string.verification_enter_code),
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )


            }
            Column {
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.verification_code),
                    style = TextStyle(
                        fontSize = 12.sp, color = FontColor
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
                        modifier = Modifier.fillMaxWidth(),
                        value = code,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.code),
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
                            code = it
                            onCode(code)
                            borderColor = BebeBlue
                            isNotValid = it.isEmpty()
                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        isError = isNotValid,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = Dimens.padding8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.verification_resend_message),
                    style = TextStyle(
                        fontSize = Dimens.fontSize10, color = Gray
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(start = Dimens.padding2)
                        .clickable {
                            onResendClick.invoke()
                        },
                    text = stringResource(id = R.string.verification_resend_underline_resend_again),
                    style = TextStyle(
                        fontSize = Dimens.fontSize10, color = BebeBlue
                    )
                )
                Text(
                    modifier = Modifier.padding(start = Dimens.padding2),
                    text = remainingTrialsNo,
                    style = TextStyle(
                        fontSize = Dimens.fontSize10, color = FontColor
                    )
                )
            }
            if (showTimer)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .padding(top = Dimens.padding8)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.verification_wait),
                            style = TextStyle(
                                fontSize = Dimens.fontSize12, color = Gray
                            )
                        )

                        Text(text = timeOutTxt)
                        Text(
                            modifier = Modifier.padding(start = Dimens.padding2),
                            text = stringResource(id = R.string.verification_seconds),
                            style = TextStyle(
                                fontSize = Dimens.fontSize12, color = BebeBlue
                            )
                        )
                    }
                    LottieAnimation(
                        composition,
                        progress,
                        dynamicProperties = dynamicProperties,
                        modifier = Modifier.size(50.dp)
                    )
                }
        }
    }
}

@Composable
fun CodeForgetPassword(
    validatePass: SnapshotStateList<ValidatePassword>, haveNewPass: Boolean,
    showTimer: Boolean,
    speed: Float,
    timeOutTxt: String,
    remainingTrialsNo: String,
    onResendClick: () -> Unit,
    onCode: (String) -> Unit,
    onChangePasswordClick: (String, String, String) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var errPasswordsMismatch by remember { mutableIntStateOf(0) }

    var isValidCodePassword by remember { mutableStateOf(false) }
    var isValidNewPassword by remember { mutableStateOf(false) }
    var isValidConfirmNewPassword by remember { mutableStateOf(false) }

    var isFocusCodePassword by remember { mutableStateOf(false) }
    var isFocusNewPassword by remember { mutableStateOf(false) }
    var isFocusConfirmNewPassword by remember { mutableStateOf(false) }


    var isCodeVisiblePassword by remember { mutableStateOf(false) }
    var isNewPasswordVisiblePassword by remember { mutableStateOf(false) }
    var isConfirmPasswordVisiblePassword by remember { mutableStateOf(false) }


    val keyboardController = LocalSoftwareKeyboardController.current
    var borderColor: Color = BebeBlue
    var isRecom by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (isScroll()) {
                    Offset.Zero
                } else {
                    available
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                return if (isScroll()) {
                    Velocity.Zero
                } else {
                    available
                }
            }

            private fun isScroll(): Boolean {
                return true // Your condition to enable/disable scrolling
            }
        }
    }
    errPasswordsMismatch = R.string.err_field_required
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color.White), content = {
                item {
                    ForgetPassword(
                        showTimer,
                        speed,
                        timeOutTxt,
                        remainingTrialsNo,
                        onResendClick = {
                            onResendClick()
                        }, onCode = {
                            onCode(it)
                            code = it
                            isCodeVisiblePassword = false
                        })
                    if (isCodeVisiblePassword)
                        Text(
                            modifier = Modifier.padding(
                                top = 8.dp,
                                start = Dimens.padding20,
                                end = Dimens.padding20
                            ),
                            text = stringResource(id = errPasswordsMismatch),
                            style = TextStyle(
                                fontSize = 12.sp, color = com.bitaqaty.reseller.ui.theme.Red
                            ),
                        )
                }
                item {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.DefaultMargin20)
                    ) {


                        Text(
                            modifier = Modifier.padding(top = Dimens.DefaultMargin),
                            text = stringResource(id = R.string.pass_new_pass),
                            style = TextStyle(
                                fontSize = 12.sp, color = FontColor
                            ),
                        )
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.halfDefaultMargin),
                            shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                            border = BorderStroke(Dimens.DefaultMargin0, borderColor),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = newPassword,
                                visualTransformation = if (isNewPasswordVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),

                                trailingIcon = {
                                    val image =
                                        if (isNewPasswordVisiblePassword) Icons.Filled.Visibility
                                        else Icons.Filled.VisibilityOff

                                    val description =
                                        if (isNewPasswordVisiblePassword) "Hide password" else "Show password"

                                    IconButton(onClick = {
                                        isNewPasswordVisiblePassword = !isNewPasswordVisiblePassword
                                    }) {
                                        Icon(imageVector = image, description)
                                    }
                                },
                                placeholder = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(R.string.password),
                                        style = TextStyle(color = BebeBlue)
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    cursorColor = BebeBlue, // Customize the cursor color as needed
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    errorBorderColor = Color.Transparent,
                                ),
                                onValueChange = {
                                    newPassword = it
                                    borderColor = BebeBlue
                                    isValidNewPassword = it.isEmpty()
                                    validatePass[3].isValidate = Utils.containCapLetters(it)
                                    validatePass[4].isValidate = Utils.containNumbers(it)
                                    validatePass[2].isValidate = Utils.containLetters(it)
                                    validatePass[1].isValidate = it.length >= 8
                                    validatePass[0].isValidate =
                                        it.matches(Regex(Globals.PASS_REGEX))
                                    isRecom = !isRecom
                                },
                                shape = RoundedCornerShape(8.dp),
                                isError = isValidNewPassword
                            )
                        }
                        if (isValidNewPassword)
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = stringResource(id = R.string.err_field_required),
                                style = TextStyle(
                                    fontSize = 12.sp, color = com.bitaqaty.reseller.ui.theme.Red
                                ),
                            )
                    }
                }
                items(validatePass) {
                    isRecom
                    ChangePassHint(
                        it
                    )
                }
                item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Text(
                            modifier = Modifier.padding(top = Dimens.padding40),
                            text = stringResource(id = R.string.pass_conf_new_pass),
                            style = TextStyle(
                                fontSize = 12.sp, color = FontColor
                            ),
                        )
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.halfDefaultMargin),
                            shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                            border = BorderStroke(Dimens.DefaultMargin0, borderColor),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = confirmNewPassword,
                                visualTransformation = if (isConfirmPasswordVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                                trailingIcon = {
                                    val image =
                                        if (isConfirmPasswordVisiblePassword) Icons.Filled.Visibility
                                        else Icons.Filled.VisibilityOff
                                    val description =
                                        if (isConfirmPasswordVisiblePassword) "Hide password" else "Show password"

                                    IconButton(onClick = {
                                        isConfirmPasswordVisiblePassword =
                                            !isConfirmPasswordVisiblePassword

                                    }) {
                                        Icon(imageVector = image, description)
                                    }
                                },
                                placeholder = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(R.string.pass_conf_new_pass),
                                        style = TextStyle(color = BebeBlue)
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    cursorColor = BebeBlue, // Customize the cursor color as needed
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    errorBorderColor = Color.Transparent,
                                ),
                                onValueChange = {
                                    confirmNewPassword = it
                                    borderColor = BebeBlue
                                    isValidConfirmNewPassword = it.isEmpty()
                                    if (it.isEmpty())
                                        errPasswordsMismatch = R.string.err_field_required
                                },
                                shape = RoundedCornerShape(8.dp),
                                isError = isValidConfirmNewPassword
                            )
                        }

                        if (isValidConfirmNewPassword)
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = stringResource(id = errPasswordsMismatch),
                                style = TextStyle(
                                    fontSize = 12.sp, color = com.bitaqaty.reseller.ui.theme.Red
                                ),
                            )

                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.padding30)
                            .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Blue100),
                            onClick = {
                                if (code.isEmpty()) {
                                    isValidCodePassword = true
                                    isFocusCodePassword = true
                                    isCodeVisiblePassword = true
                                } else if (newPassword.isEmpty()) {
                                    isValidNewPassword = true
                                    isFocusNewPassword = true
                                } else if (confirmNewPassword.isEmpty()) {
                                    isValidConfirmNewPassword = true
                                    isFocusConfirmNewPassword = true
                                } else if (newPassword != confirmNewPassword) {
                                    isValidConfirmNewPassword = true
                                    errPasswordsMismatch = R.string.err_passwords_mismatch
                                } else {
                                    onChangePasswordClick(
                                        newPassword,
                                        confirmNewPassword,
                                        code
                                    )
                                }

                            }) {
                            Text(
                                text = stringResource(id = R.string.change_password),
                                Modifier.padding(vertical = Dimens.padding8),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                }
            })
    }
}