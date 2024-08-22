package com.bitaqaty.reseller.ui.presentation.bankTransferList


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestsLog
import com.bitaqaty.reseller.data.model.SearchBank
import com.bitaqaty.reseller.ui.presentation.applyFilter.CustomDate
import com.bitaqaty.reseller.ui.presentation.applyFilter.DynamicSelectTextField
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.Filter
import com.bitaqaty.reseller.ui.theme.BanKTransferBackgroundColor
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.SecondaryFontColor
import kotlinx.coroutines.launch
import java.util.TimeZone

//{"dateFrom":"2024-08-22 00:00","dateTo":"2024-08-08 23:59","pageNumber":1,"pageSize":10,"requestStatus":"accepted","timeZone":"Africa/Cairo"}
@Composable
fun BankTransferListScreen(navController: NavController, modifier: Modifier) {
    val bankTransferViewModel: BankTransferListViewModel = hiltViewModel()
    var searchBank by remember { mutableStateOf(SearchBank()) }

    val bankTransferLogBody = RequestBankTransferLogBody()
    LaunchedEffect(key1 = true) {

        bankTransferLogBody.timeZone = TimeZone.getDefault().id
        bankTransferViewModel.searchBankTransfer(bankTransferLogBody)
        bankTransferViewModel.viewModelScope.launch {
            bankTransferViewModel.searchBankTransfer.collect {
                searchBank = it
            }
        }

    }
    BankTransfer(searchBank, onFilterClick = { selectedStatus, selectedDateTo, selectedDateFrom ->
        bankTransferLogBody.requestStatus = selectedStatus
        bankTransferLogBody.dateTo = selectedDateTo
        bankTransferLogBody.dateFrom = selectedDateFrom
        bankTransferViewModel.searchBankTransfer(bankTransferLogBody)
    })

}


@Composable
fun BankTransfer(searchBank: SearchBank, onFilterClick: (String, String, String) -> Unit) {
    var selectedDateTo by remember {
        mutableStateOf("")
    }
    var selectedDateFrom by remember {
        mutableStateOf("")
    }
    var selectedStatus by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = Dimens.padding16)
    ) {
        DynamicSelectTextField(
            TextAlign.Center,
            arrayOf(
                stringResource(R.string.btrr_status_all),
                stringResource(R.string.btrr_status_pending),
                stringResource(R.string.btrr_status_accepted),
                stringResource(R.string.btrr_status_rejected)
            ).toList(),
            true
        ) {
            selectedStatus = it
        }
        Box(Modifier.padding(vertical = Dimens.padding4)) {
            CustomDate(dateTo = {
                selectedDateTo = it
            }, dateFrom = {
                selectedDateFrom = it
            })
        }

        Box(Modifier.padding(vertical = Dimens.padding16)) {
            Filter {
                onFilterClick(selectedStatus, selectedDateTo, selectedDateFrom)
            }
        }
        Divider(
            modifier = Modifier
                .height(Dimens.DefaultMargin0)
                .fillMaxWidth()  // Set the thickness of the vertical line
                .background(BebeBlue)


        )
        LazyColumn(modifier = Modifier.padding(top = Dimens.padding16), content = {
            items(searchBank.requestsLogs) {
                BankTransferItem(it)
            }
        })

    }
}


@Composable
fun BankTransferItem(requestsLog: RequestsLog) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.padding4,
                horizontal = Dimens.padding16
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = BanKTransferBackgroundColor)

    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = requestsLog.transferPersonName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = LightGrey400
                ),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = requestsLog.creationDate,
                style = TextStyle(
                    color = LightGrey200
                ),
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.padding4),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.wrapContentSize()) {

                }
                Column(Modifier.wrapContentSize()) {
                    Text(
                        text = stringResource(id = R.string.amount),
                        style = TextStyle(
                            color = LightGrey200
                        ),
                    )
                    Text(
                        text = requestsLog.amount + " " + requestsLog.getCurrentCurrency(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = FontColor
                        ),
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(3f)
                ) {
                    Text(
                        text = requestsLog.getCurrentBankName(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = LightGrey200
                        ),
                    )
                    Text(
                        text = requestsLog.transferDate,
                        style = TextStyle(
                            color = LightGrey200
                        ),
                    )
                }
                Card(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(
                            vertical = Dimens.padding4,
                        ),
                    shape = RoundedCornerShape(Dimens.halfDefaultMargin),
                    colors = CardDefaults.cardColors(containerColor = SecondaryFontColor)

                ) {
                    Text(
                        modifier = Modifier
                            .padding(Dimens.padding4)
                            .fillMaxWidth(),
                        fontSize = Dimens.fontSize12,
                        text = requestsLog.status,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = LightGrey200
                        ),
                    )
                }

            }

        }
    }
}