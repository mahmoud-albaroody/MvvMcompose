package com.bitaqaty.reseller.ui.design.CategoryDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.home.Merchant
import com.bitaqaty.reseller.ui.design.home.components.MerchantList
import com.bitaqaty.reseller.ui.design.search.components.SearchProductList
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label

@Composable
fun CategoryDetailsScreen(){
    Column {
        Text(
            modifier = Modifier.padding(Dimens.padding12),
            text = stringResource(R.string.merchant_label),
            style = MaterialTheme.typography.Label,
            fontSize = 16.sp,
            color = label
        )
        val sampleMerchants = listOf(
            Merchant("USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USE", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USC", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("UST", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USL", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USI", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USO", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
        )
        MerchantList(sampleMerchants, LazyListState(), Color.Transparent)
        Text(
            modifier = Modifier.padding(Dimens.padding12),
            text = "Select Card",
            style = MaterialTheme.typography.Label,
            fontSize = 16.sp,
            color = label
        )
        SearchProductList(12)
    }
}

@Composable
@Preview(showSystemUi = true)
fun CategoryDetailsPreview(){
    CategoryDetailsScreen()
}