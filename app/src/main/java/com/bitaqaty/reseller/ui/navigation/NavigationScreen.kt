package com.bitaqaty.reseller.ui.navigation

import com.bitaqaty.reseller.R

sealed class Screen(
    var route: String, var icon: Int, var title: String, val objectName: String = "",
    val objectPath: String = ""
) {
    object Home : Screen("home", R.drawable.ic_home, "Home")
    object Search : Screen("search", R.drawable.ic_search, "Search")
    object Favorite : Screen("favorite", R.drawable.ic_favorite, "Favorite")
    object Store : Screen("store", R.drawable.ic_store, "Store")
    object Transactions : Screen("transactions", R.drawable.ic_transaction, "Transactions")
    object More : Screen("more", R.drawable.ic_more, "More")

    object Notification : Screen("notification", 0, "notification")

    object NotificationDetailsScreen : Screen("notificationDetails", 0, "notification")

    object RechargeLogScreen : Screen("rechargeLogScreen", 0, "rechargeLogScreen")

    object SelectMainCategoryScreen : Screen("selectMainCategoryScreen", 0, "selectMainCategoryScreen")

    object SelectSubCategoryScreen : Screen("selectSubCategoryScreen", 0, "selectSubCategoryScreen")


}
