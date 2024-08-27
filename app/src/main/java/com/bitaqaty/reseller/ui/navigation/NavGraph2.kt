package com.bitaqaty.reseller.ui.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bitaqaty.reseller.ui.presentation.accountManager.AccountManagerScreen
import androidx.navigation.navArgument
import com.bitaqaty.reseller.ui.presentation.activity.MainActivityViewModel

import com.bitaqaty.reseller.ui.presentation.activity.MainScreen
import com.bitaqaty.reseller.ui.presentation.applyFilter.ApplyFilterScreen
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferScreen
import com.bitaqaty.reseller.ui.presentation.bankTransferList.BankTransferListScreen
import com.bitaqaty.reseller.ui.presentation.categoryDetails.CategoryDetailsScreen
import com.bitaqaty.reseller.ui.presentation.changePassword.ChangePasswordScreen
import com.bitaqaty.reseller.ui.presentation.chargeBalanceScreen.ChargeBalanceScreen
import com.bitaqaty.reseller.ui.presentation.search.SearchScreen
import com.bitaqaty.reseller.ui.presentation.favoriteScreen.FavoraiteScreen
import com.bitaqaty.reseller.ui.presentation.home.HomeScreen
import com.bitaqaty.reseller.ui.presentation.login.LoginScreen
import com.bitaqaty.reseller.ui.presentation.moreScreen.MoreScreen
import com.bitaqaty.reseller.ui.presentation.notificationDetails.NotificationDetailsScreen
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationScreen
import com.bitaqaty.reseller.ui.presentation.privacyScreen.PrivacyScreen
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
import com.bitaqaty.reseller.ui.presentation.termsAndConditions.TermsAndConditionsScreen
import com.bitaqaty.reseller.ui.presentation.transactionsScreen.TransactionsScreen
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation2(
    mainViewModel: MainActivityViewModel,
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(
            Screen.Home.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable(
            Screen.Store.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            arguments = listOf(navArgument("categoryId"){
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            var categoryId = backStackEntry.arguments?.getString("categoryId")
            if(mainViewModel.showBottomNav.value){
                categoryId = null
            }
            StoreScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                modifier = modifier,
                categoryId = categoryId
            )
            //SettlementRequestScreen()
        }
        composable(
            Screen.CategoryDetails.route,
            arguments = listOf(
                navArgument("categoryId"){ type = NavType.StringType },
                navArgument("categoryName"){ type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            CategoryDetailsScreen(
                navController = navController,
                modifier = modifier,
                categoryId = categoryId!!,
            )
            mainViewModel.categoryDetailsTitle.value = categoryName!!
        }
        composable(Screen.Notification.route) {
            NotificationScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.NotificationDetailsScreen.route) {
            NotificationDetailsScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.RechargeLogScreen.route) {
            var obj: JSONObject? = null
            it.savedStateHandle.get<String>("filterObject")?.let {
                obj = JSONObject(it)
            }
//            SalesReportScreen(
//                navController = navController, modifier = modifier,
//                obj
//            )
            it.savedStateHandle.remove<String>("filterObject")
            RechargeLogScreen(navController = navController, modifier = modifier, obj)
        }
        composable(Screen.SelectMainCategoryScreen.route) {
            SelectMainCategoryScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.SelectSubCategoryScreen.route) {
            SelectSubCategoryScreen(navController = navController, modifier = modifier)
        }
        composable(
            Screen.ApplyFilterScreen.route.plus(
                Screen.ApplyFilterScreen.objectName
                        + "{comeFrom}"
            ),
            arguments = listOf(navArgument("comeFrom") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString("comeFrom")?.let { comeFrom ->
                ApplyFilterScreen(
                    navController = navController,
                    modifier = modifier,
                    comeFrom = comeFrom
                )
            }

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
        composable(Screen.BankTransferListScreen.route) {
            BankTransferListScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.Transactions.route) {
            var obj: JSONObject? = null
            it.savedStateHandle.get<String>("filterObject")?.let {
                obj = JSONObject(it)
            }

            TransactionsScreen(
                navController = navController, modifier = modifier,
                obj
            )
            it.savedStateHandle.remove<String>("filterObject")
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
            MainScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.SalesReportScreen.route) {
            var obj: JSONObject? = null
            it.savedStateHandle.get<String>("filterObject")?.let {
                obj = JSONObject(it)
            }
            SalesReportScreen(
                navController = navController, modifier = modifier,
                obj
            )
            it.savedStateHandle.remove<String>("filterObject")
        }
        composable(Screen.ProductsDiscountsScreen.route) {
            var obj: JSONObject? = null
            it.savedStateHandle.get<String>("filterObject")?.let {
                obj = JSONObject(it)
            }
            ProductsDiscountsScreen(navController = navController, modifier = modifier, obj)
            it.savedStateHandle.remove<String>("filterObject")
        }

        composable(Screen.SettlementTransactionsScreen.route) {
            SettlementTransactionsScreen(navController = navController, modifier = modifier)
        }
        composable(Screen.AccountManagerScreen.route) {
            AccountManagerScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.TermsAndConditionsScreen.route) {
            TermsAndConditionsScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            Screen.PrivacyScreen.route.plus(
                "{comeFrom}"
            ),
            arguments = listOf(navArgument("comeFrom") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString("comeFrom")?.let { comeFr ->
                PrivacyScreen(
                    navController = navController,
                    modifier = modifier, comFrom = comeFr
                )
            }

        }
    }
}

