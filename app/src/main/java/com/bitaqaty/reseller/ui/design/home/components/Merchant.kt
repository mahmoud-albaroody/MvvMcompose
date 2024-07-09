package com.bitaqaty.reseller.ui.design.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.theme.dimens

@Composable
fun Merchant(flagResId: Int, countryName: String) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(MaterialTheme.dimens.cornerRadius10),
        modifier = Modifier
            .clickable { isClicked = !isClicked }
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .wrapContentSize()

    ) {
        Row(
            modifier = Modifier
                .background(if (isClicked) Color(0xFF0D47A1) else MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
                .wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(BorderStroke(2.dp, Color.White), shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = flagResId),
                    contentDescription = "Country Flag",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                text = countryName,
                color = if (isClicked) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun MerchantList(merchants: List<Pair<Int, String>>) {
    LazyRow(
        modifier = Modifier
            .border(BorderStroke(width = 0.1.dp, color = Color.Gray)),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    ) {
        items(merchants) { country ->
            Merchant(flagResId = country.first, countryName = country.second)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Merchant(flagResId = R.drawable.flag, countryName = "USA")
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    val sampleCountries = listOf(
        Pair(R.drawable.flag, "United States"),
        Pair(R.drawable.flag, "Canada"),
        Pair(R.drawable.flag, "United Kingdom"),
        Pair(R.drawable.flag, "Germany"),
        Pair(R.drawable.flag, "France")
    )

    MerchantList(merchants = sampleCountries)
}