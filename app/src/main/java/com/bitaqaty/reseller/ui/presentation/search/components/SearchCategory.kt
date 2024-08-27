package com.bitaqaty.reseller.ui.presentation.search.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.common.ImageLoader
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun SearchCategory(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    onClickCategory: (Int) -> Unit,
){
    ImageLoader(
        modifier = modifier
            .size(80.dp)
            .clip(RoundedCornerShape(100))
            .border(
                2.dp,
                if(isSelected) Color.Blue else Color.LightGray,
                RoundedCornerShape(100)
            )
            .noRippleClickable { if (!isSelected) onClickCategory(category.id) },
        imgUrl = category.logoPath,
        errorImg = R.drawable.no_image,
        isCircle = true,
    )
    Spacer(modifier = Modifier.padding(end = Dimens.padding12))
}

//@Preview(showBackground = false)
//@Composable
//fun SearchCategoryPreview() {
//    SearchCategory()
//}