package com.bitaqaty.reseller.navigation

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
}
