package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bitaqaty.reseller.data.model.SavedAccount
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedAccountsPager(
    viewModel: BankTransferViewModel,
    savedAccounts: ArrayList<SavedAccount>
){
    val pagerState = rememberPagerState(initialPage = savedAccounts.indexOf(savedAccounts.find { it.selected }), pageCount = { savedAccounts.size})
    val coroutineScope = rememberCoroutineScope()

    Column {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(265.dp)
        ) { page ->
            SavedAccount(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                bankName = savedAccounts[page].getBankName(),
                countryName = savedAccounts[page].getCountryName(),
                senderName = savedAccounts[page].senderName,
                bankAccount = savedAccounts[page].bankAccountNumber,
                isSelected = if(!savedAccounts.any { it.selected } && page == savedAccounts.lastIndex) true else savedAccounts[page].selected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                    savedAccounts.forEach { it.selected = false }
                    savedAccounts[page].selected = true

                    if(savedAccounts.indexOf(viewModel.selectedAccount.value) != page){
                        viewModel.getSenderAccountsByCountry(savedAccounts[page].fromCountryId)
                    }
                    viewModel.onChangeSelectedAccount(savedAccounts[page])
                }
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = Color.Blue
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(if(pagerState.currentPage == iteration) 12.dp else 8.dp)
                )
            }
        }
    }
}