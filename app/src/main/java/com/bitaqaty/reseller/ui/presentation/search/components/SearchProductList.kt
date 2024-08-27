package com.bitaqaty.reseller.ui.presentation.search.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.ui.presentation.common.ProductItem
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun SearchProductList(
    products: ProductListResponse,
    isSheetStateVisible: Boolean,
    onClickProduct: (Product) -> Unit
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(Dimens.fourDefaultMargin),
    ) {
        items(products.products) { product ->
            ProductItem(
                product = product,
                isSelected = product == selectedProduct && isSheetStateVisible
            ){
                selectedProduct = product
                onClickProduct(product)
            }
        }
    }
}