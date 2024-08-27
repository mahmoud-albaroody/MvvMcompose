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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun ChildMerchantList(
    viewModel: HomeViewModel,
    childMerchants: TopChildMerchant
){
    val categoryId by viewModel.categoryId

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
                        categoryId = categoryId,
                        merchantId = childMerchant.id
                    )
                    viewModel.getProducts(productsInfo)
                },
                childMerchant = childMerchant
            )
        }
        item(span = { GridItemSpan(2) }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 10.dp)
                    .noRippleClickable { viewModel.getTopMerchants() },
                text = stringResource(id = R.string.back),
                fontFamily = frutigerLTArabic,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.End
            )
        }
    }
}