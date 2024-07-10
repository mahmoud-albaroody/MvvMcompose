package com.bitaqaty.reseller.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.FavoriteScreen
import com.bitaqaty.reseller.ui.design.HomeScreen
import com.bitaqaty.reseller.ui.design.MoreScreen
import com.bitaqaty.reseller.ui.design.SearchScreen
import com.bitaqaty.reseller.ui.design.StoreScreen
import com.bitaqaty.reseller.ui.design.TransactionsScreen
import com.bitaqaty.reseller.ui.presentation.notificationDetails.NotificationDetailsScreen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationScreen
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogScreen
import com.bitaqaty.reseller.ui.presentation.selectMainCategory.SelectMainCategoryScreen
import com.bitaqaty.reseller.ui.presentation.selectSubCategory.SelectSubCategoryScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.SelectSubCategoryScreen.route, modifier) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Search.route) {
            SearchScreen()
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen()
        }
        composable(Screen.Store.route) {
            StoreScreen()
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen()
        }
        composable(Screen.More.route) {
            MoreScreen()
        }
        composable(Screen.Notification.route) {
            NotificationScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.NotificationDetailsScreen.route) {
            NotificationDetailsScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.RechargeLogScreen.route) {
            RechargeLogScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.SelectMainCategoryScreen.route) {
            SelectMainCategoryScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.SelectSubCategoryScreen.route) {
            SelectSubCategoryScreen(navController = navController, modifier = modifier)
        }

    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(id = R.string.movie_detail)
        Screen.Search.route -> stringResource(id = R.string.artist_detail)
        Screen.Store.route -> stringResource(id = R.string.login)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
