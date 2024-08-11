package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic

@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                modifier = Modifier.background(shape = RoundedCornerShape(20.dp), color = Color.Blue),
                onClick = onConfirm
            ) {
                Text(
                    text = "Ok",
                    fontFamily = frutigerLTArabic,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    fontFamily = frutigerLTArabic,
                )
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                tint = Color.Blue
            )
        },
        title = {
            Text(
                text = "Edit Category",
                fontFamily = frutigerLTArabic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        },
        text = {
            Text(
                text = "Are you sure you want to change category ?",
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
        onConfirm = {},
        onDismiss = {}
    )
}