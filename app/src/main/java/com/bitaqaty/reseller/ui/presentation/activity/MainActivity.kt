package com.bitaqaty.reseller.ui.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Navigation
import com.bitaqaty.reseller.ui.navigation.BottomNavigationBar
import com.bitaqaty.reseller.ui.navigation.Navigation2
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.navigation.currentRoute
import com.bitaqaty.reseller.ui.navigation.navigationTitle
import com.bitaqaty.reseller.ui.presentation.appbar.AppBarWithArrow
import com.bitaqaty.reseller.ui.presentation.appbar.HomeAppBar
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition { splashViewModel.isLoading.value }
        }
        setContent {
            BitaqatyTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            Box {
                Navigation(
                    navController, modifier = Modifier
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen2(modifier: Modifier) {
    val navController = rememberNavController()
    var haveBack: Boolean = false
    var appTitle: String = ""
    var haveTopBar = true
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) },
        topBar = {
            when {
                currentRoute(navController) == Screen.NotificationDetailsScreen.route -> {
                    appTitle = "Notifications"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Notification.route -> {
                    appTitle = "Notifications"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.MyProfileScreen.route -> {
                    appTitle = "Profile"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ChargeBalanceScreen.route -> {
                    appTitle = "Charge Balance"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ChangePasswordScreen.route -> {
                    appTitle = "Change Password"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeScreen.route -> {
                    appTitle = "Recharge Using Mada"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeLogScreen.route -> {
                    appTitle = "Recharge Log"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ApplyFilterScreen.route -> {
                    appTitle = "Recharge Log"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.BankTransferScreen.route -> {
                    appTitle = "Bank Transfer"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.More.route -> {
                    appTitle = "More"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeUsingMadaScreen.route -> {
                    appTitle = "Recharge Using Mada"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Transactions.route -> {
                    appTitle = "Transactions"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.SuccessfulPurchaseScreen.route -> {
                    appTitle = "Transactions"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.SelectMainCategoryScreen.route -> {
                    appTitle = "Select Main Category"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Favorite.route -> {
                    appTitle = "Favorite"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.SelectSubCategoryScreen.route -> {
                    appTitle = "Select Sub Category"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.SalesReportScreen.route -> {
                    appTitle = "Sales Report"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Home.route -> {
                    appTitle = "Transactions"
                    haveBack = false
                    haveTopBar = false
                }

                currentRoute(navController) == Screen.SettlementTransactionsScreen.route -> {
                    appTitle = "Settlement Transactions"
                    haveBack = true
                    haveTopBar = true
                }
                currentRoute(navController) == Screen.ProductsDiscountsScreen.route -> {
                    appTitle = "Products Discount List"
                    haveBack = true
                    haveTopBar = true
                }
                currentRoute(navController) == Screen.SalesReportScreen.route -> {
                    appTitle = "Sales Report"
                    haveBack = true
                    haveTopBar = true
                }
                currentRoute(navController) == Screen.Store.route -> {
                    appTitle = "Shopping Categories"
                    haveBack = false
                    haveTopBar = true
                }
            }
            if (haveTopBar) {
                AppBarWithArrow(navigationTitle(navController, appTitle), haveBack = haveBack) {
                    navController.popBackStack()
                }
            }

        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Navigation2(
                    navController, modifier = Modifier
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        MainScreen()
    }
}

