package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.R

@Composable
fun ConfirmationDialog(
    categoryName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(
        modifier = Modifier.padding(12.dp),
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                modifier = Modifier.background(shape = RoundedCornerShape(20.dp), color = Color.Blue),
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    fontFamily = frutigerLTArabic,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    fontFamily = frutigerLTArabic,
                )
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Category",
                tint = Color.Blue
            )
        },
        title = {
            Text(
                text = stringResource(R.string.edit_category),
                fontFamily = frutigerLTArabic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.confirm_change_category, "\"$categoryName\""),
                fontFamily = frutigerLTArabic,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        },
        containerColor = Color.White,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}

@Composable
@Preview(showSystemUi = false)
fun ConfirmationDialogPreview(){
    ConfirmationDialog(
        categoryName = "itunes",
        onConfirm = {},
        onDismiss = {}
    )
}