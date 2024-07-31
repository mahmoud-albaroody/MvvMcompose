package com.bitaqaty.reseller.ui.navigation

import com.bitaqaty.reseller.R

sealed class Screen(
    var route: String, var icon: Int, var title: String, val objectName: String = "",
    val objectPath: String = ""
) {
    object Home : Screen("home", R.drawable.ic_home, "Home")
    object Search : Screen("search", R.drawable.ic_search, "Search")
    object Favorite : Screen("favorite", R.drawable.ic_favorite, "Favorite")
    object Store : Screen("store", R.drawable.ic_store, "Store")
    object Transactions : Screen("transactions", R.drawable.ic_transaction, "Transactions")
    object More : Screen("more", R.drawable.ic_more, "More")

    object Notification : Screen("notification", 0, "notification")

    object NotificationDetailsScreen : Screen("notificationDetails", 0, "notification")

    object RechargeLogScreen : Screen("rechargeLogScreen", 0, "rechargeLogScreen")

    object SelectMainCategoryScreen :
        Screen("selectMainCategoryScreen", 0, "selectMainCategoryScreen")

    object SelectSubCategoryScreen : Screen("selectSubCategoryScreen", 0, "selectSubCategoryScreen")

    object ApplyFilterScreen : Screen("applyFilterScreen", 0, "applyFilterScreen")

    object MyProfileScreen : Screen("myProfileScreen", 0, "myProfileScreen")
    object ChargeBalanceScreen : Screen("chargeBalanceScreen", 0, "chargeBalanceScreen")


    object RechargeScreen : Screen("rechargeScreen", 0, "rechargeScreen")

    object BankTransferScreen : Screen("bankTransferScreen", 0, "bankTransferScreen")

    object SuccessfulPurchaseScreen : Screen("transactionsScreen", 0, "transactionsScreen")
    object LoginScreen : Screen("loginScreen", 0, "loginScreen")

    object ResetPasswordScreen : Screen("resetPasswordScreen", 0, "resetPasswordScreen")

    object ChangePasswordScreen : Screen("changePasswordScreen", 0, "changePasswordScreen")

    object RechargeUsingMadaScreen : Screen("rechargeUsingMadaScreen", 0, "rechargeUsingMadaScreen")


    object ForgetPasswordScreen : Screen("forgetPasswordScreen", 0, "forgetPasswordScreen")
    object VerificationCodeScreen : Screen(
        "verificationCodeScreen",
        0,
        "verificationCodeScreen", objectName = "verificationCodeScreen/",
        objectPath = "{object}"
    )
    object RestorePasswordScreen : Screen(
        "restorePasswordScreen",
        0,
        "restorePasswordScreen", objectName = "restorePasswordScreen/",
        objectPath = "{username}"
    )


    object MainScreen2 : Screen("MainScreen2", 0, "MainScreen2")
    object MainScreen : Screen("MainScreen", 0, "MainScreen")
    object SalesReportScreen : Screen("SalesReportScreen", 0, "SalesReportScreen")

    object ProductsDiscountsScreen : Screen("ProductsDiscountsScreen", 0, "ProductsDiscountsScreen")

    object SettlementTransactionsScreen :
        Screen("SettlementTransactionsScreen", 0, "SettlementTransactionsScreen")

}
