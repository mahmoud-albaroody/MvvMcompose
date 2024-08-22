package com.bitaqaty.reseller.ui.presentation.changePassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.ValidatePassword
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.LightGrey50
import com.bitaqaty.reseller.ui.theme.LiteGreen
import com.bitaqaty.reseller.ui.theme.Red
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals.PASS_REGEX
import com.bitaqaty.reseller.utilities.Utils
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    modifier: Modifier
) {
    val changePasswordScreenViewModel: ChangePasswordScreenViewModel = hiltViewModel()

    val validatePass = remember { mutableStateListOf<ValidatePassword>() }
    val isValidate by remember { mutableStateOf(false) }

    validatePass.add(
        ValidatePassword(
            stringResource(id = R.string.pass_validation_1),
            isValidate
        )
    )
    validatePass.add(
        ValidatePassword(
            stringResource(id = R.string.pass_validation_2),
            isValidate
        )
    )
    validatePass.add(
        ValidatePassword(
            stringResource(id = R.string.pass_validation_3),
            isValidate
        )
    )
    validatePass.add(
        ValidatePassword(
            stringResource(id = R.string.pass_validation_4),
            isValidate
        )
    )
    validatePass.add(
        ValidatePassword(
            stringResource(id = R.string.pass_validation_5),
            isValidate
        )
    )
    SideEffect {
        changePasswordScreenViewModel.viewModelScope.launch {
            changePasswordScreenViewModel.changePassword.collect {
            }
        }
    }


    ChangePassword(
        validatePass,
        true,
        onChangePasswordClick = { newPassword, confirmNewPassword, currentPassword ->
            changePasswordScreenViewModel.changePassword(
                newPassword,
                confirmNewPassword,
                currentPassword
            )
        })
}


@Composable
fun ChangePassword(
    validatePass: SnapshotStateList<ValidatePassword>, haveNewPass: Boolean,
    onChangePasswordClick: (String, String, String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var errPasswordsMismatch by remember { mutableIntStateOf(0) }

    var isValidCurrentPassword by remember { mutableStateOf(false) }
    var isValidNewPassword by remember { mutableStateOf(false) }
    var isValidConfirmNewPassword by remember { mutableStateOf(false) }

    var isFocusCurrentPassword by remember { mutableStateOf(false) }
    var isFocusNewPassword by remember { mutableStateOf(false) }
    var isFocusConfirmNewPassword by remember { mutableStateOf(false) }


    var isPasswordVisiblePassword by remember { mutableStateOf(false) }
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
            Modifier.scrollEnabled(
                enabled = false, //provide a mutable state boolean here
            )
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color.White), content = {
                item {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.DefaultMargin)
                    ) {

                        if (haveNewPass)
                            Column(Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(top = Dimens.padding40),
                                    text = stringResource(id = R.string.pass_current_pass),
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
                                            .focusRequester(focusRequester)
                                            .onGloballyPositioned {
                                                if (isFocusCurrentPassword) {
                                                    focusRequester.requestFocus()
                                                    isFocusCurrentPassword = false
                                                }
                                            },
                                        value = currentPassword,
                                        visualTransformation = if (isPasswordVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                                        // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),

                                        trailingIcon = {
                                            val image =
                                                if (isPasswordVisiblePassword) Icons.Filled.Visibility
                                                else Icons.Filled.VisibilityOff

                                            // Please provide localized description for accessibility services
                                            val description =
                                                if (isPasswordVisiblePassword) "Hide password" else "Show password"

                                            IconButton(onClick = {
                                                isPasswordVisiblePassword =
                                                    !isPasswordVisiblePassword
                                            }) {
                                                Icon(imageVector = image, description)
                                            }
                                        },
                                        placeholder = {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = stringResource(id = R.string.pass_current_pass),
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
                                            currentPassword = it
                                            borderColor = BebeBlue
                                            isValidCurrentPassword = it.isEmpty()
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        isError = isValidCurrentPassword
                                    )
                                }
                                if (isValidCurrentPassword)
                                    Text(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = stringResource(id = R.string.err_field_required),
                                        style = TextStyle(
                                            fontSize = 12.sp, color = Red
                                        ),
                                    )
                            }

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
                                    validatePass[0].isValidate = it.matches(Regex(PASS_REGEX))
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
                                    fontSize = 12.sp, color = Red
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
                                    fontSize = 12.sp, color = Red
                                ),
                            )

                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.padding30)
                            .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Blue100),
                            onClick = {
                                if (currentPassword.isEmpty()) {
                                    isValidCurrentPassword = true
                                    isFocusCurrentPassword = true
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
                                        currentPassword
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


@Composable
fun ChangePassHint(
    hint: ValidatePassword
) {
    var color = LightGrey50
    var textColor = LightGrey400
    if (hint.isValidate) {
        color = LiteGreen
        textColor = White
    }
    Card(
        Modifier
            .padding(top = Dimens.halfDefaultMargin)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(Dimens.padding30),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                    vertical = Dimens.halfDefaultMargin
                ),
            text = hint.validateText,
            style = TextStyle(
                color = textColor,
                textAlign = TextAlign.Center
            )
        )
    }
}
fun Modifier.scrollEnabled(
    enabled: Boolean,
) = nestedScroll(
    connection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset = if(enabled) Offset.Zero else available
    }
)