package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.ui.presentation.common.ProductItem
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun ProductList(
    viewModel: HomeViewModel,
    products: ProductListResponse,
    isSheetStateVisible: Boolean,
    haveBack: Boolean = false,
    onClickProduct: (Product) -> Unit,
){
    val categoryId by viewModel.categoryId
    val isCategory by viewModel.isCategory
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin),
        columns = GridCells.Fixed(2),
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
        if(haveBack){
            item(span = { GridItemSpan(2) }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 10.dp)
                        .noRippleClickable {
                            if(isCategory){
                                viewModel.getChildMerchants(categoryId!!)
                            }else{
                                viewModel.getTopMerchants()
                            }
                        },
                    text = stringResource(id = R.string.back),
                    fontFamily = frutigerLTArabic,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}