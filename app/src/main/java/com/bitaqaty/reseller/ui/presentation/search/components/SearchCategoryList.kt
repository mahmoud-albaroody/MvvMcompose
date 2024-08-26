package com.bitaqaty.reseller.ui.presentation.search.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.search.SearchViewModel
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun SearchCategoryList(
    viewModel: SearchViewModel,
    categories: ArrayList<Category>
) {
    var selectedItem by remember { mutableStateOf<Category?>(categories.first()) }

    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(categories) { category ->
            SearchCategory(
                category = category,
                isSelected = selectedItem == category,
                onClickCategory = { index ->
                    viewModel.getMerchants(category.id)
                    selectedItem = category
                }
            )
        }
    }
}