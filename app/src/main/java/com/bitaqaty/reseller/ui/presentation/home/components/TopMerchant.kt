package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.TopMerchant
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun TopMerchantItem(
    onClick: () -> Unit,
    topMerchant: TopMerchant
) {
    Card(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Max)
            .padding(Dimens.DefaultMargin10)
            .noRippleClickable { onClick() },
        shape = RoundedCornerShape(
            topEndPercent = Dimens.cornerRadius20,
            bottomStartPercent = Dimens.cornerRadius15,
            bottomEndPercent = Dimens.cornerRadius15
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopMerchantLogo(topMerchant.logoPath)
            TopMerchantTitle(topMerchant.nameEn)
        }
    }
}

@Composable
fun TopMerchantLogo(
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
                .size(100.dp),
            imgUrl = logoUrl,
            errorImg = R.drawable.no_image,
            isCircle = false,
        )
    }
}

@Composable
fun TopMerchantTitle(
    title: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(6.dp),
            text = title.toString(),
            fontSize = 14.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            lineHeight = TextUnit.Unspecified,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopMerchantPreview() {
    TopMerchantItem(
        onClick = {},
        topMerchant = TopMerchant()
    )
}