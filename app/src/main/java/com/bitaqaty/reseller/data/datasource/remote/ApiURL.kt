package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.BuildConfig

object ApiURL {
    const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w342"

    const val BASE_URL = BuildConfig.BASE_URL
    const val GET_CATEGORIES = "categories/list-categories"
    const val TOP_MERCHANTS = "sub-acc-home/top-merchants"
    const val MERCHANTS = "merchants/list-merchants/{category_id}"
}