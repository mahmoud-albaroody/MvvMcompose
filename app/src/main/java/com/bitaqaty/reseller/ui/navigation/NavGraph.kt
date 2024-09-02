package com.bitaqaty.reseller.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.activity.MainActivityViewModel
import com.bitaqaty.reseller.ui.presentation.activity.MainScreen2
import com.bitaqaty.reseller.ui.presentation.forgetPassword.ForgetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.forgetPasswordCod.CodeForgetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.login.LoginScreen
import com.bitaqaty.reseller.ui.presentation.resetPassword.ResetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.restorePassword.RestorePasswordScreen
import com.bitaqaty.reseller.ui.presentation.verificationCode.VerificationCodeScreen


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainActivityViewModel,
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
        composable(Screen.VerificationCodeScreen.route
            .plus(Screen.VerificationCodeScreen.objectName
                + "{object}"),
            arguments = listOf(navArgument("object") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString("object")?.let { jsonString ->
                VerificationCodeScreen(
                    navController, modifier, jsonString
                )
            }
        }
        composable(Screen.CodeForgetPasswordScreen.route.plus(Screen.CodeForgetPasswordScreen.objectName
                + "{object}"),
            arguments = listOf(navArgument("object") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString("object")?.let { jsonString ->
                CodeForgetPasswordScreen(
                    navController, modifier, jsonString
                )
            }
        }
        composable(
            Screen.RestorePasswordScreen.route.plus(Screen.RestorePasswordScreen.objectName + "{username}"),
            arguments = listOf(navArgument("username") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString("username")?.let { jsonString ->
                RestorePasswordScreen(
                    navController, modifier, jsonString
                )
            }
        }


        composable(Screen.MainScreen2.route) {
          //  MainScreen2()
            MainScreen2(  mainViewModel = mainViewModel)
        }

    }
}

@Composable
fun navigationTitle(navController: NavController,title:String): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(id = R.string.movie_detail)
        Screen.Store.route -> "Shopping Categories"
        else -> {
            title
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route//.substringBeforeLast("/")
}
