package com.bitaqaty.reseller.utilities

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.White
import com.google.gson.JsonObject

@Composable
fun ShowAlertDialog(
    context: Context,
    msg: String,
    type: Globals.IconType,
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = type.name)
        },
        text = {
            Text(text = msg)
        },
        confirmButton = {
            TextButton(
                onClick = { onDismiss() }, colors = ButtonDefaults.textButtonColors(
                    contentColor = White, // Text color
                    containerColor = BebeBlue,
                    disabledContentColor = Color.Gray // Text color when button is disabled
                )
            ) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    )
}

@Composable
fun ShowRetryAlertDialog(
    context: Context,
    msg: String,
    type: Globals.IconType,
    onRetry: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onRetry() },
        title = {
            Text(text = type.name)
        },
        text = {
            Text(text = msg)
        },
        confirmButton = {
            TextButton(onClick = { onRetry() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    )
}

@Composable
fun ShowAlertDialog(
    context: Context,
    @StringRes id: Int,
    type: Globals.IconType = Globals.IconType.Warning,
    onDismiss: () -> Unit = {}
) {
    ShowAlertDialog(context, context.getString(id), type, onDismiss)
}

@Composable
fun ShowConfirmDialog(
    context: Context,
    @StringRes id: Int,
    @StringRes confirmId: Int,
    type: Globals.IconType = Globals.IconType.Warning,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(text = type.name)
        },
        text = {
            Text(text = context.getString(id))
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = stringResource(id = confirmId))
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun ShowMandatoryConfirmDialog(
    context: Context,
    content: String,
    @StringRes confirmId: Int,
    type: Globals.IconType = Globals.IconType.Warning,
    onConfirm: () -> Unit = {},
    onCancel: (() -> Unit)? = null,
    isMandatory: Boolean = true
) {
    AlertDialog(
        onDismissRequest = { if (!isMandatory) onCancel?.invoke() },
        title = {
            Text(text = type.name)
        },
        text = {
            Text(text = content)
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = stringResource(id = confirmId))
            }
        },
        dismissButton = if (!isMandatory) {
            {
                TextButton(onClick = { onCancel?.invoke() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        } else null
    )
}

@Composable
fun ShowErrorDialog(
    context: Context,
    msg: String,
    onDismiss: () -> Unit = {}
) {
    ShowAlertDialog(context, msg, Globals.IconType.Warning, onDismiss)
}

@Composable
fun ShowErrorDialog(
    context: Context,
    @StringRes id: Int,
    onDismiss: () -> Unit = {}
) {
    ShowAlertDialog(context, context.getString(id), Globals.IconType.Warning, onDismiss)
}


@Composable
fun HandleError(
    errors: SnapshotStateList<ErrorMessage>,
    showDialog: Boolean,
    navController: NavController,
    context: Context,
    goToVerificationCodeScreen: () -> Unit = {},
    onDismissClick: () -> Unit
) {

    if (errors.isNotEmpty()) {
        when (errors[0].errorCode) {
            Errors.AuthError.PasswordNotChanged.value -> {
                navController.navigate(Screen.ChangePasswordScreen.route)
//                onDismissClick()
            }

            Errors.AuthError.SmsAuthSent.value,
            Errors.AuthError.IpLocationSMSAuthSent.value -> {
                goToVerificationCodeScreen()
                //onDismissClick()
            }

            Errors.AuthError.ExceededMaxAllowedResendSmsLimit.value -> {
                if (showDialog)
                    ShowErrorDialog(
                        context, R.string.err_sms_resend_exceeded,
                        onDismiss = { onDismissClick() })
            }

            Errors.AuthError.InvalidLoginProcessToken.value -> {
                if (showDialog)
                    ShowErrorDialog(
                        context, R.string.reset_access_data,
                        onDismiss = { onDismissClick() })
            }

            Errors.AuthError.INVALID_USERNAME.value -> {
                if (showDialog)
                    ShowErrorDialog(
                        context, R.string.invalid_username,
                        onDismiss = { onDismissClick() })
            }

            Errors.AuthError.InvalidSmsVerificationCode.value -> {
                if (showDialog)
                    ShowErrorDialog(
                        context, R.string.err_invalid_verification_code,
                        onDismiss = { onDismissClick() })
            }

            Errors.AuthError.NewPassEqualsOldPass.value -> {
                R.string.err_new_pass_match_old_pass
            }

            Errors.AuthError.IncorrectCurrentPassword.value -> {
                R.string.err_wrong_current_pass
            }

            Errors.AuthError.ExceededMaxAllowedResendSmsLimit.value -> {
                ShowErrorDialog(context, R.string.err_sms_resend_exceeded)
            }
//            Errors.AuthError.InvalidLoginProcessToken.value -> {
//                R.string.err_session_ended
//            }
            Errors.AuthError.NewPassNotEqualsConfPass.value -> {
                R.string.err_passwords_mismatch
            }

            Errors.AuthError.ExceededMaxAllowedSms.value -> {
                ShowErrorDialog(
                    context, R.string.err_sms_exceeded
                )
            }

            Errors.AuthError.ExceededMaxAllowedResendSmsLimit.value -> {
                ShowErrorDialog(context, R.string.err_sms_resend_exceeded)
            }

            Errors.AuthError.EXCEEDEDALLOWEDVERIFICATIOTRIALS.value -> {
                ShowErrorDialog(
                    context,
                    R.string.err_sms_resend_exceeded
                )
            }

            "-1" -> {
                if (showDialog)
                    ShowErrorDialog(
                        context,
                        R.string.err_server,
                        onDismiss = { onDismissClick() })
            }

            else -> {
                if (showDialog)
                    ShowErrorDialog(
                        context,
                        R.string.err_incorrect_username_password, onDismiss = {
                            onDismissClick()
                        }
                    )

            }
        }
    }
}