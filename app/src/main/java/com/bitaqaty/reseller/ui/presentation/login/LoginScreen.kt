package com.bitaqaty.reseller.ui.presentation.login


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.data.model.RechargingLog
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.Grey
import com.bitaqaty.reseller.ui.theme.Red
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.HandleError
import com.bitaqaty.reseller.utilities.Utils
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier) {
    val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val errors = remember { SnapshotStateList<ErrorMessage>() }
    var username by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    LaunchedEffect(key1 = 0) {
        loginScreenViewModel.viewModelScope.launch {
            loginScreenViewModel.signInState.collect {
                it.data?.let { data ->
                    if (data.errors?.isNotEmpty() == true) {
                        errors.clear()
                        errors.addAll(it.data.errors!!)
                        showDialog = true
                    } else {
                        token = data.loginProcessToken?.substringBefore("/").toString()
                        loginScreenViewModel.authenticatedLogin(token)
                    }
                }
            }
        }
        loginScreenViewModel.viewModelScope.launch {
            loginScreenViewModel.authenticatedLoginState.collect {
                it.data.let { data ->
                    if (it.data?.errors?.isNotEmpty() == true) {
                        errors.clear()
                        errors.addAll(it.data.errors)
                        showDialog = true
                    } else {
                        Utils.saveUserData(data)
                        navController.navigate(Screen.MainScreen2.route)
                    }
                }
            }
        }
    }
    errors.apply {
        HandleError(this, showDialog,
            navController, context, goToVerificationCodeScreen = {
                val jsonObject = JsonObject()
                jsonObject.addProperty("mobileNumber", username)
                jsonObject.addProperty("token", token)
                jsonObject.addProperty("comeFrom", "Login")

                navController.navigate(
                    Screen.VerificationCodeScreen
                        .route.plus(
                            Screen.VerificationCodeScreen.objectName
                                    + "$jsonObject"
                        )
                )
            }, onDismissClick = {
                showDialog = false
            })
    }



    LoginItem(onResetAccessClick = {
        navController.navigate(Screen.ResetPasswordScreen.route)
    }, onForgetClick = {
        navController.navigate(Screen.ForgetPasswordScreen.route)

    }, onLoginClick = { userName, password ->
        username = userName
        loginScreenViewModel.signIn(userName = userName, password = password)
    })
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginItem(
    onResetAccessClick: () -> Unit, onForgetClick: () -> Unit,
    onLoginClick: (userName: String, password: String) -> Unit
) {
    val configuration = LocalConfiguration.current
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
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(vertical = 50.dp, horizontal = 24.dp),
    ) {
        Column(
            Modifier.padding(top = Dimens.DefaultMargin20),
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
                    text = stringResource(id = R.string.sign_instruction),
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )
            }
            Column {
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.sign_username),
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
                                text = stringResource(id = R.string.sign_username),
                                style = TextStyle(color = Grey)
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
                            isNotValid = !isValidText(it)
                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        isError = isNotValid,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() })
                    )
                }


                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = stringResource(id = R.string.password),
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
                                text = stringResource(R.string.password),
                                style = TextStyle(color = Grey)
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
                            password = it
                            borderColor = BebeBlue
                            isValidNotPassword = it.isEmpty()
                        },
                        shape = RoundedCornerShape(8.dp),
                        isError = isValidNotPassword
                    )
                }
            }


        }
        if (isNotValid || isValidNotPassword) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.fourDefaultMargin)
                .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                colors = ButtonDefaults.buttonColors(Red),
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
                        //       viewModel.login(email, password)
                    } else {
                        if (email.isEmpty()) {
                            borderColor = Red
                            isNotValid = true
                        }
                        if (password.isEmpty()) {
                            borderColor = Red
                            isValidNotPassword = true
                        }

                    }
                }) {
                Text(
                    text = stringResource(R.string.err_incorrect_username_password),
                    style = TextStyle(
                        textAlign = TextAlign.Center, fontSize = 13.sp,
                        color = White
                    )
                )
            }
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Blue100),
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
                    onLoginClick.invoke(email, password)
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
                text = stringResource(R.string.login),
                Modifier.padding(vertical = Dimens.padding8),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
        }


        AccessData(onForgetClick = {
            onForgetClick.invoke()
        }, onResetAccessClick = {
            onResetAccessClick.invoke()
        })

    }
}

@Composable
fun AccessData(
    onResetAccessClick: () -> Unit, onForgetClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = Dimens.padding30),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.clickable {
                onForgetClick.invoke()
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.background(White),
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Logo",
            )
            Text(
                modifier = Modifier.padding(start = Dimens.halfDefaultMargin),
                text = stringResource(id = R.string.sign_forgot_password),
                style = TextStyle(
                    fontSize = 13.sp, color = BebeBlue
                ),
            )
        }
        Row(
            modifier = Modifier
                .clickable {
                    onResetAccessClick.invoke()
                }
                .padding(Dimens.halfDefaultMargin), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.background(White),
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Logo",
            )
            Text(
                text = stringResource(id = R.string.reset_access_data),
                modifier = Modifier.padding(start = Dimens.halfDefaultMargin),
                style = TextStyle(
                    fontSize = 13.sp, color = BebeBlue
                ),
            )
        }
    }
}

fun isValidText(text: String): Boolean {
    // Add your custom validation rules here
    return text.isNotEmpty()
}

