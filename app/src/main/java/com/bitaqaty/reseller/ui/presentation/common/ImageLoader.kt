package com.bitaqaty.reseller.ui.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.bitaqaty.reseller.R

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    imgUrl: String?,
    @DrawableRes errorImg: Int,
    isCircle: Boolean,
){
    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
        .data(imgUrl)
        .placeholder(R.drawable.loading)
        .error(errorImg)

    if (isCircle) {
        imageRequestBuilder.transformations(CircleCropTransformation())
    }

    val imageRequest = imageRequestBuilder.build()

    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}