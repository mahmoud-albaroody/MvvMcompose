package com.bitaqaty.reseller.ui.design.bottom_navigation

import com.bitaqaty.reseller.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Search : NavigationItem("search", R.drawable.ic_search, "Search")
    object Favorite : NavigationItem("favorite", R.drawable.ic_favorite, "Favorite")
    object Store : NavigationItem("store", R.drawable.ic_store, "Store")
    object Transactions : NavigationItem("transactions", R.drawable.ic_transaction, "Transactions")
    object More : NavigationItem("more", R.drawable.ic_more, "More")
}
