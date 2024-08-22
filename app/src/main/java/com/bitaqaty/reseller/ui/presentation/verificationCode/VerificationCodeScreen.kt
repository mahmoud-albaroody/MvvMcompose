package com.bitaqaty.reseller.ui.presentation.verificationCode


import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey50
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.localized
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerificationCodeScreen(
    navController: NavController, modifier: Modifier, jsonString: String
) {
    val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
    val mobileNumber = jsonObject.get("mobileNumber").asString
    val token = jsonObject.get("token").asString
    val context = LocalContext.current
    var showTimer by remember { mutableStateOf(false) }
    var timeOutTxt by remember { mutableStateOf("") }
    var remainingTrialsNo by remember { mutableStateOf("") }
    var speed by remember { mutableFloatStateOf(0f) }

    val verificationCodeViewModel: VerificationCodeViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
      //  verificationCodeViewModel.resendResetAccessDataSms(token)
        verificationCodeViewModel.viewModelScope.launch {
            verificationCodeViewModel.validateResetAccessData.collect {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
        verificationCodeViewModel.viewModelScope.launch {
            verificationCodeViewModel.remainingTrials.collect { remainingTrials ->
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
                    CoroutineScope(IO).launch {
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
                        context, remainingTrials.getWaitTime(), verificationCodeViewModel
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
        verificationCodeViewModel.viewModelScope.launch {
            verificationCodeViewModel.resendResetAccess.collect { resend ->
                verificationCodeViewModel.getRemainingTrials(token)
            }
        }


    }
    VerificationCode(
        showTimer,
        speed,
        timeOutTxt,
        remainingTrialsNo,
        mobileNumber,
        onResendClick = {
            //  verificationCodeViewModel.getRemainingTrials(token)
            verificationCodeViewModel.resendResetAccessDataSms(token)
        },
        onVerifyClick = {
            verificationCodeViewModel.validateResetVerificationCode(token, it)
        })
}

fun updateWaitTime(ctx: Context, resendSmsLink: Double,
                   viewModel: VerificationCodeViewModel) {
    var sec = resendSmsLink * 60
    CoroutineScope(IO).launch {
        while (sec >= 0) {
            if (sec <= 0) {
                viewModel.timer.postValue("-1")
            } else {
                viewModel.timer.postValue(getRemainingTime(ctx, sec))
            }
            sec -= 1
            delay(1000)
        }
    }
}

private fun getRemainingTime(ctx: Context, sec: Double): String {
    var min = sec / 60
    return when {
        (min > 1) -> {
            if (!Utils.isWhole(min)) {
                min += 1
            }
            " ${min.toInt()} ${ctx.getString(R.string.mins)} "
        }

        (sec > 0) -> {
            " ${sec.toInt()} ${ctx.getString(R.string.secs)} "
        }

        else -> {
            ""
        }
    }
}

@Composable
fun VerificationCode(
    showTimer: Boolean,
    speed: Float,
    timeOutTxt: String,
    remainingTrialsNo: String,
    mobile: String,
    onResendClick: () -> Unit,
    onVerifyClick: (String) -> Unit
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
    val mobileNumber: String = if (Utils.getLang() != "en") {
        stringResource(R.string.message_containing_verification_code).replace(
            "0411", mobile.substringAfterLast("*").localized() + "*******"
        )
    } else {
        stringResource(id = R.string.message_containing_verification_code).replace(
            "0411", mobile
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(
                vertical = 50.dp, horizontal = 24.dp
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
                    text = stringResource(id = R.string.verification_enter_code),
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )

                Text(
                    modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
                    text = mobileNumber,
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

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = Blue,
                disabledContainerColor = LightGrey50,
                disabledContentColor = FontColor,
            ),
            enabled = !showTimer,
            onClick = {
                if (code.isNotEmpty() && !isNotValid) {
                    onVerifyClick(code)
                } else {
                    if (code.isEmpty()) {
                        isNotValid = true
                    }


                }
            }) {
            Text(
                text = stringResource(id = R.string.verify),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


    }
}


@Preview
@Composable
fun VerificationCodee(

) {
    var email by rememberSaveable { mutableStateOf("") }
    var mobile by rememberSaveable { mutableStateOf("") }
    var isNotValid by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var borderColor: Color = BebeBlue
    val isPlaying by remember {
        mutableStateOf(true)
    }

// for speed
    val speed by remember {
        mutableFloatStateOf(0.2f)
    }
// remember lottie composition, which
// accepts the lottie composition result
    val composition by rememberLottieComposition(

        LottieCompositionSpec
            // here `code` is the file name of lottie file
            // use it accordingly
            .RawRes(R.raw.resend_timer)
    )

    // to control the animation
    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,

        // Iterates Forever
        iterations = LottieConstants.IterateForever,

        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play
        isPlaying = isPlaying,

        // pass speed we created above,
        // changing speed will increase Lottie
        speed = speed,

        // this makes animation to restart
        // when paused and play
        // pass false to continue the animation
        // at which it was paused
        restartOnPlay = false

    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = SimpleColorFilter(BebeBlue.toArgb()),
            keyPath = arrayOf("**")
        )
    )
    val mobileNumber: String = if (Utils.getLang() != "en") {
        stringResource(R.string.message_containing_verification_code).replace(
            "0411", mobile.substringAfterLast("*").localized() + "*******"
        )
    } else {
        stringResource(id = R.string.message_containing_verification_code).replace(
            "0411", mobile
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(
                vertical = 50.dp, horizontal = 24.dp
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
                    text = stringResource(id = R.string.verification_enter_code),
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )

                Text(
                    modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
                    text = mobileNumber,
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
                        value = email,
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
                            email = it
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
                        fontSize = Dimens.fontSize12, color = Gray
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(start = Dimens.padding2)
                        .clickable {

                        },
                    text = stringResource(id = R.string.verification_resend_underline_resend_again),
                    style = TextStyle(
                        fontSize = Dimens.fontSize12, color = BebeBlue
                    )
                )
                Text(
                    modifier = Modifier.padding(start = Dimens.padding2),
                    text = "1/3",
                    style = TextStyle(
                        fontSize = Dimens.fontSize12, color = FontColor
                    )
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .padding(top = Dimens.padding8)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.verification_wait), style = TextStyle(
                            fontSize = Dimens.fontSize12, color = Gray
                        )
                    )

                    Text(text = "1/2")
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

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(BebeBlue),
            onClick = {
                if (email.isNotEmpty() && !isNotValid) {
                    //   onResetClick(email)
                } else {
                    if (email.isEmpty()) {
                        isNotValid = true
                    }


                }
            }) {
            Text(
                text = stringResource(id = R.string.verify),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


    }
}

