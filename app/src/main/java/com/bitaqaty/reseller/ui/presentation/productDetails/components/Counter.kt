package com.bitaqaty.reseller.ui.presentation.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsViewModel
import com.bitaqaty.reseller.ui.theme.Counter
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.counterText
import com.bitaqaty.reseller.ui.theme.merchantBg
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun Counter(
    viewModel: ProductDetailsViewModel
) {
    val counter by viewModel.counter

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.cornerRadius10), clip = false)
            .background(Color.Transparent, shape = RoundedCornerShape(Dimens.cornerRadius10))
            .border(color = merchantBg, shape = RoundedCornerShape(Dimens.cornerRadius10), width = 2.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .noRippleClickable { viewModel.onDecrease() }
                    .padding(horizontal = 18.dp),
                text = "-",
                style = MaterialTheme.typography.Counter,
                color = counterText,
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.94f)
                    .width(2.dp),
                color = merchantBg,
            )
            Text(
                text = counter.toString(),
                color = counterText,
                style = MaterialTheme.typography.Counter,
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.94f)
                    .width(2.dp),
                color = merchantBg,
            )
            Text(
                modifier = Modifier
                    .noRippleClickable { viewModel.onIncrease() }
                    .padding(horizontal = 18.dp),
                text = "+",
                color = counterText,
                style = MaterialTheme.typography.Counter,
            )
        }
    }
}

//@Preview(showBackground = false)
//@Composable
//fun CounterPreview() {
//    Counter()
//}