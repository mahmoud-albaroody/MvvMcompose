package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun SenderCountries(
    viewModel: BankTransferViewModel,
    countries: AccountsCountries? = null,
) {
    val countriesMap = mutableMapOf<String, String>()

    countries?.lookupList?.forEach {
        countriesMap[it.getName()] = it.id.toString()
    }

    Card(
        Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)

    ) {
        Box {
            if (countriesMap.isNotEmpty())
                DynamicSelectTextField(
                    TextAlign.Center, countriesMap.keys.toList(), clickable = true,
                    isBankCountry = true, selectedAccount = viewModel.selectedAccount.value
                ) {
                    viewModel.getSenderAccountsByCountry(countriesMap[it].toString())
                }
        }
    }
}