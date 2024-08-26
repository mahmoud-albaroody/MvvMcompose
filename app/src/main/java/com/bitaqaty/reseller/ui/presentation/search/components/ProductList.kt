package com.bitaqaty.reseller.ui.presentation.search.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.ui.presentation.common.ProductItem
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun ProductList(
    products: ProductListResponse,
    isSheetStateVisible: Boolean,
    onClickProduct: (Product) -> Unit
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
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