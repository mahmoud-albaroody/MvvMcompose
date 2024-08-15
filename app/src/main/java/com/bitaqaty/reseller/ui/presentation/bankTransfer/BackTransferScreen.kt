package com.bitaqaty.reseller.ui.presentation.bankTransfer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Account
import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestOneCardAccountsBody
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.recharge.RechargeAmount
import com.bitaqaty.reseller.ui.theme.BanKTransferBackgroundColor
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.merchantLabel
import kotlinx.coroutines.launch

@Composable
fun BankTransferScreen(navController: NavController, modifier: Modifier) {
    val bankTransferViewModel: BankTransferViewModel = hiltViewModel()
    var accounts by remember { mutableStateOf(AccountsByCountry()) }
    var countries by remember { mutableStateOf(AccountsCountries()) }

    val requestOneCardAccountsBody = RequestOneCardAccountsBody()
    LaunchedEffect(key1 = true) {
        bankTransferViewModel.onecardCountries()
        bankTransferViewModel.viewModelScope.launch {
            bankTransferViewModel.onecardAccount.collect {
                accounts = it
            }
        }
        bankTransferViewModel.viewModelScope.launch {
            bankTransferViewModel.onecardCountries.collect {
                countries = it
                requestOneCardAccountsBody.countryId = countries.lookupList?.get(0)?.id
                requestOneCardAccountsBody.resellerUsername =
                    com.bitaqaty.reseller.utilities.Utils.getUserData()?.reseller?.username
                bankTransferViewModel.onecardAccount(requestOneCardAccountsBody)
            }
        }
    }

    BankTransfer(accounts, countries) { selectedContry ->
        requestOneCardAccountsBody.countryId =
            countries.lookupList?.find { it.getName() == selectedContry }?.id
        requestOneCardAccountsBody.resellerUsername =
            com.bitaqaty.reseller.utilities.Utils.getUserData()?.reseller?.username
        bankTransferViewModel.onecardAccount(requestOneCardAccountsBody)
    }
}


@Composable
fun BankTransfer(
    accounts: AccountsByCountry,
    countries: AccountsCountries,
    onCountrySelect: (String) -> Unit
) {

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        DynamicSelectTextField(
            TextAlign.Start,
            stringArrayResource(R.array.credit_mada_instruction_arr).toList(), true
        ) {

        }
        StepsIcons()

        RechargeAmount(countries) {
            onCountrySelect(it)
        }

        if (!accounts.accounts.isNullOrEmpty())
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.halfDefaultMargin), content = {
                    items(accounts.accounts) {
                        BankTransferItem(it)
                    }
                })
    }
}


@Composable
fun BankTransferItem(account: Account) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.halfDefaultMargin),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = BanKTransferBackgroundColor)

    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.halfDefaultMargin),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(Dimens.halfDefaultMargin)
                        .size(25.dp),
                    model = account.oneCardBankLogoPath,
                    contentDescription = null,
                )
                Text(
                    text = account.getBankName(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = LightGrey400
                    ),
                )
            }
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.DefaultMargin10),
                shape = RoundedCornerShape(0),

                colors = CardDefaults.cardColors(containerColor = Color.White)

            ) {
                BankTransferTextItem(
                    stringResource(id = R.string.btrr_account_number),
                    account.getAccountNumber()
                )
                BankTransferTextItem(
                    stringResource(id = R.string.btrr_account_name),
                    account.getAccountName()
                )
                BankTransferTextItem(
                    stringResource(id = R.string.btrr_bank_address),
                    account.getAccountAddress()
                )
                BankTransferTextItem(
                    stringResource(id = R.string.btrr_iban),
                    account.getIban()
                )

            }
        }
    }
}


@Composable
fun BankTransferTextItem(text: String, text1: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.fourDefaultMargin,
                horizontal = Dimens.DefaultMargin
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = LightGrey400, fontSize = 12.sp
            ),
        )
        Text(
            text = text1,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = merchantLabel, fontSize = 12.sp
            ),
            modifier = Modifier.padding(start = Dimens.fourDefaultMargin)
        )
    }
}

@Preview
@Composable
fun StepsIcons() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(Dimens.halfDefaultMargin),
            painter = painterResource(R.drawable.ic_manager_icon),
            contentDescription = ""
        )
        Divider(
            modifier = Modifier
                .height(Dimens.DefaultMargin0)
                .fillMaxWidth()
                .padding(horizontal = Dimens.DefaultMargin)
                .weight(1f)
                .background(BebeBlue)
        )
        Image(
            modifier = Modifier.padding(Dimens.halfDefaultMargin),
            painter = painterResource(R.drawable.ic_manager_icon),
            contentDescription = ""
        )
        Divider(
            modifier = Modifier
                .height(Dimens.DefaultMargin0)
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = Dimens.DefaultMargin)
                .background(BebeBlue)
        )
        Image(
            modifier = Modifier.padding(Dimens.halfDefaultMargin),
            painter = painterResource(R.drawable.ic_manager_icon),
            contentDescription = ""
        )
    }

}