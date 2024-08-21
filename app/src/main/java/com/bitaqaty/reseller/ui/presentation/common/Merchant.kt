package com.bitaqaty.reseller.ui.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.clickedMerchant
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.ui.theme.merchantBg
import com.bitaqaty.reseller.ui.theme.merchantLabel
import com.bitaqaty.reseller.utilities.noRippleClickable


@Composable
fun MerchantItem(
    merchant: Merchant,
    isSelected: Boolean,
    onClickMerchant: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(Dimens.cornerRadius10),
        modifier = Modifier
            .noRippleClickable { if(!isSelected) onClickMerchant() }
            .padding(horizontal = Dimens.padding8)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .background(if (isSelected) clickedMerchant else merchantBg)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .width(100.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.White), shape = CircleShape)
            ) {
                ImageLoader(
                    modifier = Modifier
                        .size(Dimens.merchantImage)
                        .clip(CircleShape),
                    imgUrl = merchant.logoPath,
                    errorImg = R.drawable.no_image_small,
                    isCircle = true,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                text = merchant.getName(),
                color = if (isSelected) Color.White else merchantLabel,
                style = TextStyle(
                    fontFamily = frutigerLTArabic,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MerchantPreview() {
//    val merchant = Merchant(
//        name = "USA",
//        imageUrl = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"
//    )
//    MerchantItem(
//        merchant = merchant,
//        isSelected = false,
//        onClickMerchant = {}
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MerchantListPreview() {
//    val sampleMerchants = listOf(
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//        Merchant("USA", ""),
//    )
//
//    MerchantList(merchants = sampleMerchants, scrollState = LazyListState())
//}