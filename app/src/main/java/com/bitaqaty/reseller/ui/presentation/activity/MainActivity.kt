package com.bitaqaty.reseller.ui.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.ui.navigation.Navigation
import com.bitaqaty.reseller.ui.navigation.BottomNavigationBar
import com.bitaqaty.reseller.ui.navigation.Navigation2
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.navigation.currentRoute
import com.bitaqaty.reseller.ui.navigation.navigationTitle
import com.bitaqaty.reseller.ui.presentation.appbar.AppBarWithArrow
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.utilities.Globals.DEV_ID
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.LocaleHelper
import com.bitaqaty.reseller.utilities.Utils.loadLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadLocale(this)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition { mainActivityViewModel.isLoading.value }
        }
        DEV_ID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        setContent {
            BitaqatyTheme {
                Log.e("token", CurrentUser.getInstance()?.token.toString())
                if (CurrentUser.getInstance()?.token == null) {
                    MainScreen(mainActivityViewModel)
                } else {
                    MainScreen2(mainActivityViewModel)
                }
            }
        }
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, language = lang)
        )
    }
}


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainScreen(mainViewModel: MainActivityViewModel) {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            content = { innerPadding ->
                Box {
                    Navigation(
                        mainViewModel = mainViewModel,
                        navController = navController,
                        modifier = Modifier
                    )
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainScreen2( mainViewModel: MainActivityViewModel) {
        val navController = rememberNavController()
        var haveBack: Boolean = false
        var appTitle: String = ""
        var haveSubTitle = false
        var haveTopBar = true
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            bottomBar = { if (mainViewModel.showBottomNav.value) BottomNavigationBar(navController) },
            topBar = {
                when {
                    currentRoute(navController) == Screen.NotificationDetailsScreen.route -> {
                        appTitle = stringResource(id = R.string.notification)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Notification.route -> {
                        appTitle = stringResource(id = R.string.notification)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.MyProfileScreen.route -> {
                        appTitle = stringResource(R.string.profile)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.ChargeBalanceScreen.route -> {
                        appTitle = stringResource(R.string.charge_balance)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.ChangePasswordScreen.route -> {
                        appTitle = stringResource(id = R.string.change_password)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.RechargeScreen.route -> {
                        appTitle = "Recharge Using Mada"
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.RechargeLogScreen.route -> {
                        appTitle = stringResource(id = R.string.rechargeLog)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.ApplyFilterScreen.route -> {
                        appTitle = stringResource(id = R.string.rechargeLog)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.BankTransferScreen.route -> {
                        appTitle = stringResource(id = R.string.bank_transfer)
                        haveBack = true
                        haveTopBar = true
                    }

                    currentRoute(navController) == Screen.BankTransferListScreen.route -> {
                        appTitle = stringResource(id = R.string.bank_transfer)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.More.route -> {
                        appTitle = stringResource(id = R.string.more)
                        haveBack = false
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.RechargeUsingMadaScreen.route -> {
                        appTitle = "Recharge Using Mada"
                        haveBack = false
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Transactions.route -> {
                        appTitle = stringResource(id = R.string.tranaction_log)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SuccessfulPurchaseScreen.route -> {
                        appTitle = stringResource(id = R.string.purchase_success)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SelectMainCategoryScreen.route -> {
                        appTitle = "Select Main Category"
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Favorite.route -> {
                        appTitle = "Favorite"
                        haveBack = false
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SelectSubCategoryScreen.route -> {
                        appTitle = "Select Sub Category"
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SalesReportScreen.route -> {
                        appTitle = stringResource(id = R.string.sales_report)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Home.route -> {
                        appTitle = stringResource(id = R.string.tranaction_log)
                        haveBack = false
                        haveTopBar = false
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Search.route -> {
                        haveBack = false
                        haveTopBar = false
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SettlementTransactionsScreen.route -> {
                        appTitle = stringResource(id = R.string.settlement_transaction)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                currentRoute(navController) == Screen.AccountManagerScreen.route -> {
                    appTitle = stringResource(id = R.string.account_manager)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.TermsAndConditionsScreen.route -> {
                    appTitle = stringResource(id = R.string.termsAndConditions)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.PrivacyScreen.route -> {
                    appTitle = stringResource(id = R.string.more_privacy_policy)
                    haveBack = true
                    haveTopBar = true
                }



                    currentRoute(navController) == Screen.ProductsDiscountsScreen.route -> {
                        appTitle = stringResource(id = R.string.product_discount_list)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SalesReportScreen.route -> {
                        appTitle = stringResource(id = R.string.sales_report)
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.Store.route -> {
                        appTitle = "Shopping Categories"
                        haveBack = !mainViewModel.showBottomNav.value
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.CategoryDetails.route -> {
                        appTitle = mainViewModel.categoryDetailsTitle.value
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = false
                    }

                    currentRoute(navController) == Screen.SettlementRequestScreen.route -> {
                        appTitle = "Settlement Request"
                        haveBack = true
                        haveTopBar = true
                        haveSubTitle = true
                    }
                }
                if (haveTopBar) {
                    AppBarWithArrow(
                        navigationTitle(navController, appTitle),
                        haveSubTitle,
                        haveBack = haveBack
                    ) {
                        mainViewModel.showBottomNav.value = true
                        navController.popBackStack()
                    }
                }

            },
            content = { contentPadding ->
                Box(modifier = Modifier.padding(contentPadding)) {
                    Navigation2(
                        navController = navController,
                        modifier = Modifier,
                        mainViewModel = mainViewModel
                    )
                }
            }
        )
    }



