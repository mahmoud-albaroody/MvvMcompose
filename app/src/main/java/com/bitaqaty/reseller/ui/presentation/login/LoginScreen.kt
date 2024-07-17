package com.bitaqaty.reseller.ui.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier) {
    val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    LoginItem(onResetAccessClick = {
        navController.navigate(Screen.ResetPasswordScreen.route)
    }, onForgetClick = {
        navController.navigate(Screen.ForgetPasswordScreen.route)

    }, onLoginClick = {
        navController.navigate(Screen.MainScreen2.route)
    })
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginItem(
    onResetAccessClick: () -> Unit, onForgetClick: () -> Unit, onLoginClick: () -> Unit
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
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(vertical = 50.dp, horizontal = 24.dp),
    ) {


        Column(
            Modifier.padding(top = Dimens.DefaultMargin20),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Welcome to\nBitaqaty Business",
                    style = TextStyle(
                        fontSize = 22.sp, color = FontColor
                    ),
                )
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = "To login, please enter your email address and password And click on Sign in",
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )
            }
            Column {
                Text(
                    modifier = Modifier.padding(top = Dimens.DefaultMargin),
                    text = "Email address/User name",
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
                        modifier = Modifier.fillMaxWidth(),

                        value = email,
                        placeholder = {
                            Text(
                                text = "Email address/User name",
                                style = TextStyle(color = Color.Gray)
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = BebeBlue // Customize the cursor color as needed
                            ,
                            errorBorderColor = Color.Transparent
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
                                text = stringResource(R.string.password),
                                style = TextStyle(color = Color.Gray)
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = BebeBlue // Customize the cursor color as needed
                            ,
                            errorBorderColor = Color.Transparent
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
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
                        //       viewModel.login(email, password)
                    } else {
                        if (email.isEmpty()) {
                            borderColor = Color.Red
                            isNotValid = true
                        }
                        if (password.isEmpty()) {
                            borderColor = Color.Red
                            isValidNotPassword = true
                        }

                    }
                }) {
                Text(
                    text = stringResource(R.string.login), style = TextStyle(
                        textAlign = TextAlign.Center, fontSize = 13.sp, color = Color.White
                    )
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
                onLoginClick.invoke()

//                if (email.isNotEmpty() && password.isNotEmpty() && !isNotValid) {
//
//                } else {
//                    if (email.isEmpty()) {
//                        isNotValid = true
//                    }
//                    if (password.isEmpty()) {
//                        isValidNotPassword = true
//                    }
//
//                }
            }) {
            Text(
                text = stringResource(R.string.login),
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
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
                modifier = Modifier.background(Color.White),
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Logo",
            )
            Text(
                modifier = Modifier.padding(start = Dimens.halfDefaultMargin),
                text = "Forget Password",
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
                modifier = Modifier.background(Color.White),
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Logo",
            )
            Text(
                text = "Reset Access Data",
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
    return text.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(text).matches()
}
