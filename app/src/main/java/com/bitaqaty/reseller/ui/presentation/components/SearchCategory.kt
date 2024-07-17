package com.bitaqaty.reseller.ui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.border

@Composable
fun SearchCategory(){
    Image(
        painter = painterResource(R.drawable.ic_star),
        contentDescription = "Circle Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(Dimens.categoryImage)
            .clip(CircleShape)
            .border(Dimens.borderThickness7, border, CircleShape)
    )
    Spacer(modifier = Modifier.padding(end = Dimens.padding12))
}

@Preview(showBackground = false)
@Composable
fun SearchCategoryPreview() {
    SearchCategory()
}