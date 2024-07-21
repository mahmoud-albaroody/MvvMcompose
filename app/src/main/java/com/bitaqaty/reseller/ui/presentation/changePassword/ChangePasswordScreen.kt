package com.bitaqaty.reseller.ui.presentation.changePassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LightGrey50
import com.bitaqaty.reseller.ui.theme.merchantLabel

@Composable
fun ChangePasswordScreen(navController: NavController, modifier: Modifier) {
    val changePasswordScreenViewModel: ChangePasswordScreenViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    ChangePassword()
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChangePassword() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isNotValid by remember { mutableStateOf(false) }
    var isValidNotPassword by remember { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    var borderColor: Color = BebeBlue
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.DefaultMargin)
        ) {
            Text(
                modifier = Modifier.padding(top = Dimens.DefaultMargin),
                text = "Password",
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
                        .fillMaxWidth()
                        .focusRequester(focusRequester),

//                keyboardActions =KeyboardOptions(imeAction = ImeAction.Next) ,
                    value = password,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),

                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description =
                            if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = BebeBlue // Customize the cursor color as needed
                        , errorBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        password = it
                        borderColor = BebeBlue
                        isValidNotPassword = it.isEmpty()
                    },
                    shape = RoundedCornerShape(8.dp),
                    isError = isValidNotPassword
                )
            }
            LazyColumn(
                Modifier.background(Color.White), content = {
                    items(4) {
                        ChangePassHint()
                    }
                })
            Text(
                modifier = Modifier.padding(top = Dimens.padding40),
                text = "Confirm New Password",
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
                        .fillMaxWidth()
                        .focusRequester(focusRequester),

//                keyboardActions =KeyboardOptions(imeAction = ImeAction.Next) ,
                    value = password,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),

                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description =
                            if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Confirm New Password",
                            style = TextStyle(color = BebeBlue)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = BebeBlue // Customize the cursor color as needed
                        , errorBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        password = it
                        borderColor = BebeBlue
                        isValidNotPassword = it.isEmpty()
                    },
                    shape = RoundedCornerShape(8.dp),
                    isError = isValidNotPassword
                )
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.padding30)
                .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Blue100),
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
                        //       viewModel.login(email, password)
                    } else {
                        if (email.isEmpty()) {
                            isNotValid = true
                        }
                        if (password.isEmpty()) {
                            isValidNotPassword = true
                        }

                    }
                }) {
                Text(
                    text = "Change",
                    Modifier.padding(vertical = Dimens.padding8),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                )
            }

        }
    }
}

@Preview
@Composable
fun ChangePassHint() {
    Card(
        Modifier.padding(top = Dimens.halfDefaultMargin),
        shape = RoundedCornerShape(Dimens.padding30),
        colors = CardDefaults.cardColors(containerColor = LightGrey50)
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                    vertical = Dimens.halfDefaultMargin
                ),
            text = "1 Should be written in English",
            style = TextStyle(color = LightGrey400, textAlign = TextAlign.Center)
        )
    }
}