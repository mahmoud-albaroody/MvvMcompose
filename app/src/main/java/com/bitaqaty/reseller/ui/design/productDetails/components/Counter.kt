package com.bitaqaty.reseller.ui.design.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.ui.design.productDetails.noRippleClickable
import com.bitaqaty.reseller.ui.theme.Counter
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.counterText
import com.bitaqaty.reseller.ui.theme.merchantBg

@Composable
fun Counter() {
    var counter by remember { mutableIntStateOf(1) }

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
                    .noRippleClickable { if (counter > 1) counter-- }
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
                    .noRippleClickable { counter++ }
                    .padding(horizontal = 18.dp),
                text = "+",
                color = counterText,
                style = MaterialTheme.typography.Counter,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CounterPreview() {
    Counter()
}