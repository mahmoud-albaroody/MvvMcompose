package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.BuildConfig

object ApiURL {
    const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w342"

    const val BASE_URL = BuildConfig.BASE_URL
    const val CATEGORIES = "categories/list-categories"
    const val TOP_MERCHANTS = "sub-acc-home/top-merchants"
    const val SUB_ACCOUNT_HOME_CHILD = "sub-acc-home/list-child-merchants"
    const val MERCHANTS = "merchants/list-merchants/{category_id}"
    const val PRODUCT_LIST = "products/list-products"
    const val CATEGORY_EDIT = "categories/edit-category-order/{currentCategoryId}/{newCategoryId}"

    const val SYSTEM_SETTING = "system-settings/system-settings"
    const val SETTLEMENT_REQUEST_DATA = "partner-settlement/get-settelement-request-data"
    const val CREATE_SETTLEMENT_REQUEST = "partner-settlement/create-settelement-request"
    const val PROFILE = "auth/get-reseller"

    const val PURCHASE_ORDER = "shopping-cart/purchase-order"
}