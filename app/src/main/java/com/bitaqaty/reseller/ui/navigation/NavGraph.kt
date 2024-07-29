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
import com.bitaqaty.reseller.ui.presentation.activity.MainScreen2
import com.bitaqaty.reseller.ui.presentation.forgetPassword.ForgetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.login.LoginScreen
import com.bitaqaty.reseller.ui.presentation.resetPassword.ResetPasswordScreen


@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.LoginScreen.route, modifier) {

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.ForgetPasswordScreen.route) {
            ForgetPasswordScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.MainScreen2.route) {
            MainScreen2( modifier = modifier)
        }

    }
}

@Composable
fun navigationTitle(navController: NavController,title:String): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(id = R.string.movie_detail)
        Screen.Search.route -> stringResource(id = R.string.artist_detail)
        Screen.Store.route -> "Shopping Categories"
        else -> {
            title
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
