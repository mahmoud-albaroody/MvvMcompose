package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel
import com.bitaqaty.reseller.ui.theme.SearchBarBackground

@Composable
fun AccountNum(
    viewModel: BankTransferViewModel
) {
    val accountNum by viewModel.accountNum
    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = accountNum,
            onValueChange = {
                viewModel.onChangeAccountNum(it)
                isError = it.isEmpty()
            },
            textStyle = TextStyle(fontSize = 16.sp),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SearchBarBackground,
                unfocusedContainerColor = SearchBarBackground,
                focusedTextColor = Color.Blue,
                cursorColor = Color.Gray,
                focusedBorderColor = Color.Blue,
                errorContainerColor = Color.Transparent,
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
        )

        if (isError) {
            Text(
                text = stringResource(id = R.string.err_field_required),
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}