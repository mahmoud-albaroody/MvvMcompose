package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.PlaceHolder
import com.bitaqaty.reseller.ui.theme.clickedMerchant
import com.bitaqaty.reseller.ui.theme.merchantBg
import com.bitaqaty.reseller.ui.theme.merchantLabel


//@Composable
//fun MerchantItem(
//    merchant: Merchant,
//    isSelected: Boolean,
//    onClickMerchant: () -> Unit
//) {
//    Card(
//        shape = RoundedCornerShape(Dimens.cornerRadius10),
//        modifier = Modifier
//            .clickable { onClickMerchant() }
//            .padding(horizontal = Dimens.padding8)
//            .wrapContentSize()
//
//    ) {
//        Row(
//            modifier = Modifier
//                .background(if (isSelected) clickedMerchant else merchantBg)
//                .padding(Dimens.padding8)
//                .wrapContentSize()
//        ) {
//            Box(
//                modifier = Modifier
//                    .border(BorderStroke(2.dp, Color.White), shape = CircleShape)
//            ) {
//                val imageRequest = ImageRequest.Builder(LocalContext.current)
//                    .data(merchant.imageUrl)
//                    .placeholder(R.drawable.ic_search)
//                    .error(R.drawable.ic_store)
//                    .transformations(CircleCropTransformation())
//                    .build()
//                AsyncImage(
//                    model = imageRequest,
//                    contentDescription = "Country Flag",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(Dimens.merchantImage)
//                        .clip(CircleShape)
//                )
//            }
//            Spacer(modifier = Modifier.width(Dimens.padding8))
//            Text(
//                modifier = Modifier
//                    .wrapContentSize()
//                    .align(Alignment.CenterVertically)
//                    .padding(end = Dimens.padding12),
//                text = merchant.getName(),
//                color = if (isSelected) Color.White else merchantLabel,
//                style = MaterialTheme.typography.PlaceHolder,
//            )
//        }
//    }
//}
//
//@Composable
//fun MerchantList(
//    merchants: List<Merchant>,
//    scrollState: LazyListState,
//    borderColor: Color = Color.Gray
//) {
//    var selectedMerchant by remember { mutableStateOf<Merchant?>(null) }
//    LazyRow(
//        modifier = Modifier
//            .border(BorderStroke(width = 0.1.dp, color = borderColor)),
//        contentPadding = PaddingValues(vertical = Dimens.padding8, horizontal = Dimens.padding12),
//        state = scrollState
//    ) {
//        items(merchants) { merchant ->
//            MerchantItem(
//                merchant = merchant,
//                isSelected = merchant == selectedMerchant,
//                onClickMerchant = { selectedMerchant = merchant }
//            )
//        }
//    }
//}



