package com.bitaqaty.reseller.navigation

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
import com.bitaqaty.reseller.ui.design.Home
import com.bitaqaty.reseller.ui.design.MoreScreen
import com.bitaqaty.reseller.ui.design.Search
import com.bitaqaty.reseller.ui.design.StoreScreen
import com.bitaqaty.reseller.ui.design.TransactionsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            Home()
        }
        composable(Screen.Search.route) {
            Search()
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
