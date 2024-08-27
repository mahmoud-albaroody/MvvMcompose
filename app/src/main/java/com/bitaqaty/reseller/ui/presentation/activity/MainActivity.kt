package com.bitaqaty.reseller.ui.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
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
import com.bitaqaty.reseller.ui.presentation.appbar.HomeAppBar
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.LocaleHelper
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.loadLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()
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
            setKeepOnScreenCondition { splashViewModel.isLoading.value }
        }
        setContent {
            BitaqatyTheme {

                Log.e("token", CurrentUser.getInstance()?.token.toString())
                if (CurrentUser.getInstance()?.token == null) {
                    MainScreen()
                } else {
                    MainScreen2()
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

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen2() {
    val navController = rememberNavController()
    var haveBack = false
    var appTitle = ""
    var haveTopBar = true
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) },
        topBar = {
            when {
                currentRoute(navController) == Screen.NotificationDetailsScreen.route -> {
                    appTitle = stringResource(id = R.string.notification)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Notification.route -> {
                    appTitle = stringResource(id = R.string.notification)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.MyProfileScreen.route -> {
                    appTitle = stringResource(R.string.profile)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ChargeBalanceScreen.route -> {
                    appTitle = stringResource(R.string.charge_balance)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ChangePasswordScreen.route -> {
                    appTitle = stringResource(id = R.string.change_password)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeScreen.route -> {
                    appTitle = "Recharge Using Mada"
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeLogScreen.route -> {
                    appTitle = stringResource(id = R.string.rechargeLog)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.ApplyFilterScreen.route -> {
                    appTitle = stringResource(id = R.string.rechargeLog)
                    haveBack = true
                    haveTopBar = true
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
                }


                currentRoute(navController) == Screen.More.route -> {
                    appTitle = stringResource(id = R.string.more)
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.RechargeUsingMadaScreen.route -> {
                    appTitle = "Recharge Using Mada"
                    haveBack = false
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Transactions.route -> {
                    appTitle = stringResource(id = R.string.tranaction_log)
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
                    appTitle = stringResource(id = R.string.sales_report)
                    haveBack = true
                    haveTopBar = true
                }

                currentRoute(navController) == Screen.Home.route -> {
                    appTitle = stringResource(id = R.string.tranaction_log)
                    haveBack = false
                    haveTopBar = false
                }

                currentRoute(navController) == Screen.SettlementTransactionsScreen.route -> {
                    appTitle = stringResource(id = R.string.settlement_transaction)
                    haveBack = true
                    haveTopBar = true
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
                }

                currentRoute(navController) == Screen.SalesReportScreen.route -> {
                    appTitle = stringResource(id = R.string.sales_report)
                    haveBack = true
                    haveTopBar = true
                }
            }
            if (haveTopBar) {
                AppBarWithArrow(navigationTitle(navController, appTitle), haveBack = haveBack) {
                    navController.popBackStack()
                }
            }

        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                Navigation2(
                    navController, modifier = Modifier
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        MainScreen()
    }
}

