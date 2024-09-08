package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R

@Composable
fun AddingBankName(){
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier.padding(vertical = 12.dp),
            painter = painterResource(id = R.drawable.ic_sort),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        ValidationTextField(
            placeHolder = stringResource(id = R.string.btrr_add_bank_name)
        )
    }
}