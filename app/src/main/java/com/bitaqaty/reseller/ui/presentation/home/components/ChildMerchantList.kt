package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun ChildMerchantList(
    viewModel: HomeViewModel,
    childMerchants: TopChildMerchant
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimens.fourDefaultMargin),
    ) {
        items(childMerchants.merchants!!.toList()) { childMerchant ->
            ChildMerchantItem(
                onClick = {
                    val productsInfo = ProductListRequest(
                        categoryId = viewModel._categoryId.value!!,
                        merchantId = childMerchant.id
                    )
                    viewModel.getProducts(productsInfo)
                },
                childMerchant = childMerchant
            )
        }
    }
}