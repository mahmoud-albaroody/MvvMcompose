package com.bitaqaty.reseller.ui.presentation.restorePassword


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.White
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

@Composable
fun RestorePasswordScreen(
    navController: NavController,
    modifier: Modifier,
    jsonString: String
) {
    val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
    val email = jsonObject.get("email").asString
    val resetPasswordViewModel: RestorePasswordViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        resetPasswordViewModel.viewModelScope.launch {
            resetPasswordViewModel.resetPassword.collect {
                it.resetPasswordToken?.let { it1 -> resetPasswordViewModel.getRemainingTrials(it1) }
            }
        }
        resetPasswordViewModel.viewModelScope.launch {
            resetPasswordViewModel.remainingTrials.collect {

            }
        }
    }
    RestorePassword(
        jsonString,
        stringResource(id = R.string.to_reset_access_data)
    ) {  methodSelected ->
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email)
        jsonObject.addProperty("verificationType", methodSelected)
        navController.navigate(
            Screen.CodeForgetPasswordScreen
                .route.plus(
                    Screen.CodeForgetPasswordScreen.objectName
                            + "$jsonObject"
                )
        )
    }
}

@Composable
fun RestorePassword(
    text: String,
    text2: String? = null,
    onResetClick: ( methodSelected: String) -> Unit
) {
    var methodSelected by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
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
                    text = stringResource(id = R.string.reset_password),
                    style = TextStyle(
                        fontSize = 14.sp, color = FontColor, fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.reset_password_choose_code),
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )
            }

            Column {
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.send_verification_code_via),
                    style = TextStyle(
                        fontSize = 12.sp, color = FontColor
                    ),
                )

            }
        }
        SimpleRadioButtonComponent(text) {
            methodSelected = it
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(BebeBlue),
            onClick = {
                onResetClick( methodSelected)
            }) {
            Text(
                text = stringResource(id = R.string.send),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


    }
}


@Composable
fun SimpleRadioButtonComponent(jsonString: String, onMethodSelected: (String) -> Unit) {
    val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
    val email = jsonObject.get("email").asString
    val mobile = jsonObject.get("mobileNumber").asString

    val radioOptions: List<String> = if (!email.contains("@")) {
        listOf(email, mobile)

    } else {
        listOf(mobile)
    }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    if (selectedOption == email) {
        onMethodSelected("EMAIL")
    } else {
        onMethodSelected("SMS")
    }

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),


        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {

                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    Text(
                        text = text,
                        color = FontColor,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}