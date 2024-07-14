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
import com.bitaqaty.reseller.ui.design.SearchScreen
import com.bitaqaty.reseller.ui.design.StoreScreen
import com.bitaqaty.reseller.ui.presentation.activity.MainScreen2
import com.bitaqaty.reseller.ui.presentation.applyFilter.ApplyFilterScreen
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferScreen
import com.bitaqaty.reseller.ui.presentation.changePassword.ChangePasswordScreen
import com.bitaqaty.reseller.ui.presentation.chargeBalanceScreen.ChargeBalanceScreen
import com.bitaqaty.reseller.ui.presentation.forgetPassword.ForgetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.login.LoginScreen
import com.bitaqaty.reseller.ui.presentation.moreScreen.MoreScreen
import com.bitaqaty.reseller.ui.presentation.notificationDetails.NotificationDetailsScreen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationScreen
import com.bitaqaty.reseller.ui.presentation.profileScreen.MyProfileScreen
import com.bitaqaty.reseller.ui.presentation.recharge.RechargeScreen
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogScreen
import com.bitaqaty.reseller.ui.presentation.resetPassword.ResetPasswordScreen
import com.bitaqaty.reseller.ui.presentation.selectMainCategory.SelectMainCategoryScreen
import com.bitaqaty.reseller.ui.presentation.selectSubCategory.SelectSubCategoryScreen
import com.bitaqaty.reseller.ui.presentation.successfulPurchase.SuccessfulPurchaseScreen
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.TransactionsScreen

@Composable
fun Navigation2(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
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
        composable(Screen.ApplyFilterScreen.route) {
            ApplyFilterScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.MyProfileScreen.route) {
            MyProfileScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.ChargeBalanceScreen.route) {
            ChargeBalanceScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.MoreScreen.route) {
            MoreScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.RechargeScreen.route) {
            RechargeScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.BankTransferScreen.route) {
            BankTransferScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.SuccessfulPurchaseScreen.route) {
            SuccessfulPurchaseScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController = navController, modifier = modifier)
        }



    }
}

