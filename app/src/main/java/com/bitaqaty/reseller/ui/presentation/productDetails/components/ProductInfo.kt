package com.bitaqaty.reseller.ui.presentation.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun ProductInfo(onClickInfo: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(Dimens.cornerRadius20))
                    .border(
                        width = 2.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(Dimens.cornerRadius20)
                    )
                    .padding(8.dp)
                    .noRippleClickable { onClickInfo() },
                imageVector = ImageVector.vectorResource(R.drawable.ic_info),
                contentDescription = "Info Icon",
                tint = Color.Blue,
            )
        }

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(start = Dimens.padding12)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.total_amount),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = frutigerLTArabic,
                color = Color(0xFF8E8E8E),
                textAlign = TextAlign.Center
            )
            Text(
                text = "80.87 SAR",
                fontFamily = frutigerLTArabic,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF535353),
                lineHeight = TextUnit.Unspecified,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductInfoPreview() {
    ProductInfo(onClickInfo = {})
}