package com.bitaqaty.reseller.ui.presentation.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.ui.presentation.home.components.MerchantItem
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun SearchMerchantList(
    viewModel: SearchViewModel,
    merchants: List<Merchant>,
) {
    var selectedMerchant by remember { mutableStateOf(if(merchants.isEmpty()) null else merchants.first()) }
    val scrollState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = Dimens.padding8, horizontal = Dimens.padding4),
        state = scrollState
    ) {
        items(merchants) { merchant ->
            MerchantItem(
                merchant = merchant,
                isSelected = merchant == selectedMerchant,
                onClickMerchant = {
                    selectedMerchant = merchant

                    val productsInfo = ProductListRequest(
                        categoryId = viewModel._categoryId.value!!,
                        merchantId = merchant.id
                    )
                    viewModel.getProducts(productsInfo)
                }
            )
        }
    }
}