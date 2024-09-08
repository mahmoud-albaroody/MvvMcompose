package com.bitaqaty.reseller.ui.presentation.bankTransfer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.SavedAccounts
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.AccountNum
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.AddingBankName
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.SaveAccountCheckBox
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.SavedAccountsPager
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.SenderAccounts
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.SenderCountries
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.StepNavigation
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.ValidationTextField
import com.bitaqaty.reseller.ui.presentation.bankTransfer.components.Warning
import com.bitaqaty.reseller.utilities.Utils
import kotlinx.coroutines.launch

@Composable
fun BankTransferStep2(
    viewModel: BankTransferViewModel,
    savedAccounts: SavedAccounts,
    senderCountries: AccountsCountries,
    senderAccounts: AccountsCountries,
    onClickBack: () -> Unit
){
    var isToAddNewAccount by remember { mutableStateOf(false) }
    val isOtherSelected by viewModel.isOtherSelected

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState)
    ) {
        if(savedAccounts.savedAccounts != null && !isToAddNewAccount){
            Button(
                onClick = {
                    isToAddNewAccount = true
                    viewModel.onChangeAccountNum("")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1D3893)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.btrr_add_account),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.btrr_saved_accounts),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            SavedAccountsPager(
                viewModel,
                savedAccounts.savedAccounts!!
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.btrr_sender_country),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        SenderCountries(
            viewModel = viewModel,
            countries = senderCountries,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.btrr_sender_bank_name),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        SenderAccounts(
            viewModel = viewModel,
            accounts = senderAccounts
        )
        if(isOtherSelected){
            Spacer(modifier = Modifier.height(12.dp))
            AddingBankName()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.btrr_sender_bank_no),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        AccountNum(viewModel = viewModel)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.btrr_sender_name),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        ValidationTextField(
            value = Utils.getUserData()?.reseller?.fullName ?: ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        Warning()
        Spacer(modifier = Modifier.height(16.dp))
        SaveAccountCheckBox(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        StepNavigation(
            onClickBack = onClickBack
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BankTransferStep2Preview(){
//    BankTransferStep2(
//        viewModel = hiltViewModel()
//    )
//}