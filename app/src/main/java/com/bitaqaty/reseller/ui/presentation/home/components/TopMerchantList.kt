package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.ui.theme.Dimens

@Composable
fun TopMerchantList(
    topMerchants: TopMerchants
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.halfDefaultMargin),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimens.fourDefaultMargin),
    ) {
        items(topMerchants.merchants?.size!!) { index ->
            TopMerchantItem(onClick = {  }, topMerchant = topMerchants.merchants[index])
        }
    }
}