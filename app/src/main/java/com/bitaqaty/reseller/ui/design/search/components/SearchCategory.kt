package com.bitaqaty.reseller.ui.design.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.theme.border
import com.bitaqaty.reseller.ui.design.theme.dimens

@Composable
fun SearchCategory(){
    Image(
        painter = painterResource(R.drawable.ic_star),
        contentDescription = "Circle Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(MaterialTheme.dimens.categoryImage)
            .clip(CircleShape)
            .border(MaterialTheme.dimens.borderThickness7, border, CircleShape)
    )
    Spacer(modifier = Modifier.padding(end = MaterialTheme.dimens.padding12))
}

@Preview(showBackground = false)
@Composable
fun SearchCategoryPreview() {
    SearchCategory()
}