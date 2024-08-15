package com.bitaqaty.reseller.ui.presentation.home.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun ProductItem(
    product: Product,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .padding(Dimens.DefaultMargin10)
            .noRippleClickable { if(product.isInStock()) onClick() },
        shape = RoundedCornerShape(
            topEndPercent = Dimens.cornerRadius20,
            bottomStartPercent = Dimens.cornerRadius15,
            bottomEndPercent = Dimens.cornerRadius15
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .alpha(if(!product.isInStock()) 0.25f else 1f)
                    .background(if (isSelected) Color(0xFF3255A4) else Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProductLogo(product.productSmallImagePath)
                ProductPrice(
                    product.getRecommendedRetailPriceDouble().toString(),
                    isSelected
                )
            }
            if(!product.isInStock()){
                Text(
                    text = stringResource(id = R.string.out_of_stock),
                    color = Color.DarkGray,
                    fontFamily = frutigerLTArabic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ProductLogo(
    logoUrl: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(
            topEndPercent = 20,
            bottomStartPercent = 20,
            bottomEndPercent = 20
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ImageLoader(
            modifier = Modifier
                .fillMaxWidth()
                .size(120.dp),
            imgUrl = logoUrl,
            errorImg = R.drawable.no_image,
            isCircle = false
        )
    }
}

@Composable
fun ProductPrice(
    price: String? = null,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = price.toString(),
            fontSize = 14.sp,
            color = if(isSelected) Color.White else Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            lineHeight = TextUnit.Unspecified,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp),
            text = stringResource(id = R.string.sar),
            fontSize = 10.sp,
            color = if(isSelected) Color.White else Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            lineHeight = TextUnit.Unspecified
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProductPreview() {
//    ProductItem(product = Product(recommendedRetailPrice = "200.0")) {
//
//    }
//}