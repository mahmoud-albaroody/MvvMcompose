package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.R

@Composable
fun SenderAccounts(
    viewModel: BankTransferViewModel,
    accounts: AccountsCountries?,
) {
    val accountsMap = mutableMapOf<String, String>()

    accounts?.lookupList?.forEach {
        accountsMap[it.getName()] = it.id.toString()
    }
    accountsMap["Other"] = ""

    Card(
        Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)

    ) {
        Box {
            if (accountsMap.isNotEmpty())
                DynamicSelectTextField(
                    TextAlign.Center, accountsMap.keys.toList(), clickable = true,
                    isBankName = true, selectedAccount = viewModel.selectedAccount.value
                ) {
                    if(it == "Other"){
                        viewModel.isOtherSelected(true)
                    }else{
                        viewModel.isOtherSelected(false)
                    }
                }
        }
    }
}