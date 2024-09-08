package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel

@Composable
fun SaveAccountCheckBox(
    viewModel: BankTransferViewModel
){
    val isSaved by viewModel.isSaved
    var isChecked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(20.dp),
            checked = isChecked,
            onCheckedChange = {
                if(!isSaved){
                    isChecked = it
                }else{
                    showDialog = true
                    isChecked = false
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Blue,
                uncheckedColor = Color.Gray,
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.btrr_save_sender_bank),
            fontSize = 12.sp
        )
    }
    if(showDialog){
        SaveAccountDialog {
            showDialog = false
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SaveAccountCheckBoxPreview(){
//    SaveAccountCheckBox(isSaved = true)
//}