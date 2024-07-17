package com.bitaqaty.reseller.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.bitaqaty.reseller.ui.presentation.activity.MainScreen
import com.bitaqaty.reseller.ui.presentation.applyFilter.ApplyFilterScreen
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferScreen
import com.bitaqaty.reseller.ui.presentation.changePassword.ChangePasswordScreen
import com.bitaqaty.reseller.ui.presentation.chargeBalanceScreen.ChargeBalanceScreen
import com.bitaqaty.reseller.ui.presentation.components.SearchScreen
import com.bitaqaty.reseller.ui.presentation.favoriteScreen.FavoraiteScreen
import com.bitaqaty.reseller.ui.presentation.home.HomeScreen
import com.bitaqaty.reseller.ui.presentation.moreScreen.MoreScreen
import com.bitaqaty.reseller.ui.presentation.notificationDetails.NotificationDetailsScreen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationScreen
import com.bitaqaty.reseller.ui.presentation.productsDiscountsList.ProductsDiscountsScreen
import com.bitaqaty.reseller.ui.presentation.profileScreen.MyProfileScreen
import com.bitaqaty.reseller.ui.presentation.recharge.RechargeScreen
import com.bitaqaty.reseller.ui.presentation.rechargeUsingMadaSuccessfully.RechargeUsingMadaScreen
import com.bitaqaty.reseller.ui.presentation.rechargingLogScreen.RechargeLogScreen
import com.bitaqaty.reseller.ui.presentation.salesReport.SalesReportScreen
import com.bitaqaty.reseller.ui.presentation.selectMainCategory.SelectMainCategoryScreen
import com.bitaqaty.reseller.ui.presentation.selectSubCategory.SelectSubCategoryScreen
import com.bitaqaty.reseller.ui.presentation.settlementTransaction.SettlementTransactionsScreen
import com.bitaqaty.reseller.ui.presentation.store.StoreScreen
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

        composable(Screen.Store.route) {
            StoreScreen(navController = navController, modifier = modifier)
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
        composable(Screen.More.route) {
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


        composable(Screen.RechargeUsingMadaScreen.route) {
            RechargeUsingMadaScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.Favorite.route) {
            FavoraiteScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.MainScreen.route) {
            MainScreen()
        }
        composable(Screen.SalesReportScreen.route) {
            SalesReportScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.ProductsDiscountsScreen.route) {
            ProductsDiscountsScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.SettlementTransactionsScreen.route) {
            SettlementTransactionsScreen(navController = navController, modifier = modifier)
        }

    }
}

