package com.bitaqaty.reseller.ui.presentation.resetPassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor

@Composable
fun ResetPasswordScreen(navController: NavController, modifier: Modifier) {
    val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    ResetPassword(
        "To reset your access data.\n" +
                "Please enter your username, and click send.\n" +
                "A message  message containing verfication code\n" +
                "will be sent to you, please use that code to reset your\n" +
                "Access data", "Please note that link is valid within 3 minutes"
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(
    text: String, text2: String
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isNotValid by remember { mutableStateOf(false) }
    var isValidNotPassword by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val (focusRequester) = FocusRequester.createRefs()
    var borderColor: Color = BebeBlue
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(vertical = 50.dp, horizontal = 24.dp),
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.DefaultMargin20),
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
                    text = text,
                    style = TextStyle(
                        fontSize = 13.sp, color = FontColor
                    ),
                )
                Text(
                    modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
                    text = text2,
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
                        modifier = Modifier
                            .fillMaxWidth(),

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
                            , errorBorderColor = Color.Transparent
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


            }


        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding30)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(BebeBlue),
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
                text = "Send",
                Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


    }
}

fun isValidText(text: String): Boolean {
    // Add your custom validation rules here
    return text.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(text).matches()
}

