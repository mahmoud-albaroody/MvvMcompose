package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.BuildConfig

object ApiURL {

    const val BASE_URL = BuildConfig.BASE_URL
    const val CATEGORY_EDIT = "categories/edit-category-order/{currentCategoryId}/{newCategoryId}"

    const val SIGN_IN = "auth/login"
    const val LOGIN_CHANGE_PASSWORD = "auth/login-change-password"
    const val RESET_CHANGE_PASSWORD = "auth/forget-password-change"
    const val CHANGE_PASSWORD = "profile/change-password"
    const val AUTHENTICATED_LOGIN = "auth/authenticated-login"
    const val PROFILE = "auth/get-reseller"

    const val ACCESS_DATA = "auth/validate-username-access-data"

    const val REMAINING_TRAILS = "auth/sms-verification-remaining-trials"
    const val VERIFICATION_REMAINING_TRAILS = "auth/verification-remaining-trials"
    const val VALIDATE_SMS_VERIFICATION_CODE = "auth/validate-sms-verification-code"
    const val RESEND_SMS_VERIFICATION_CODE = "auth/resend-reset-access-data-sms"
    const val VALIDATE_RESET_SMS_VERIFICATION_CODE = "auth/validate-reset-access-data-sms"

    const val ACCEPT_AGREEMENT = "auth/accept-agreement"
    const val FORGET_PASSWORD = "auth/forget-password"
    const val FORGET_PASSWORD_INIT = "auth/forget-password-init"
    const val FORGET_PASSWORD_SEND = "auth/forget-password-send"
    const val VERIFY_FORGET_PASSWORD = "auth/verify-forget-password"
    const val LOGOUT = "auth/logout"
    const val SYSTEM_SETTINGS = "system-settings/system-settings"

    const val White_Label = "auth/white-label-system-setting"

    //Notification
    const val Notification = "https://app.onesignal.com/api/v1/notifications"
    // https://app.onesignal.com/api/v1/notifications?app_id=f0f96b49-9937-4d51-9d30-74630eb2c7c8&limit=10&offset=0

    //<editor-fold desc="Bank transfer">
    const val SEARCH_BANK_TRANSFER_REQUEST = "bank-transfer/search-bank-transfer-requests"
    const val EXPORT_BANK_TRANSFER_REQUEST = "bank-transfer/export-bank-transfer-requests"
    const val ONE_CARD_COUNTRIES = "bank-transfer/list-onecard-accounts-countries"
    const val ONE_CARD_ACCOUNTS = "bank-transfer/list-onecard-accounts-by-country"
    const val SAVED_ACCOUNTS = "bank-transfer/list-saved-accounts"
    const val SENDER_COUNTRIES = "bank-transfer/list-sender-countries"
    const val SENDER_ACCOUNTS_BY_COUNTRY = "bank-transfer/list-sender-banks-by-country/{id}"
    const val DELETE_SENDER_ACCOUNT = "bank-transfer/delete-saved-account"
    const val CALCULATE_RECHARGE_AMOUNT = "bank-transfer/calculate-recharge-amount"
    const val SAVE_SENDER_ACCOUNT = "bank-transfer/save-or-update-bank-account"
    const val PLACE_REQUEST = "bank-transfer/place-bank-transfer-request"
    //</editor-fold>

    //<editor-fold desc="Manage sub account">
    const val MANAGE_SUB_ACCOUNT_LIST = "manage-sub-accounts/list-sub-accounts"
    const val UPDATE_SUB_ACCOUNT = "manage-sub-accounts/update-sub-account"
    const val ADD_SUB_ACCOUNT = "manage-sub-accounts/add-new-sub-account"
    const val GET_SUB_ACCOUNT_PERMISSIONS = "manage-sub-accounts/get_sub_account_permission"
    const val UPDATE_SUB_ACCOUNTBalance = "manage-sub-accounts/update-sub-account_balance"
    const val RESET_SUB_ACCOUNT_LIMIT = "manage-sub-accounts/reset-sub-account_limit/{subAccountId}"
    const val EXPORT_SUB_ACCOUNT_LIST = "manage-sub-accounts/export-sub-accounts"
    //</editor-fold>

    //<editor-fold desc="Sales report">
    const val GET_USER_NAME_LIST = "sales-report/list-sub-accounts"
    const val GET_PRODUCT_LOOKUP = "lookups/list-products"
    const val GENERATE_SALES_REPORT = "sales-report/generate-sales-report"
    const val EXPORT_SALES_REPORT = "sales-report/export-sales-report"
    //</editor-fold>

    //<editor-fold desc="Support">
    const val GET_TICKET_TYPE = "support/get-ticket-types"
    const val ADD_TICKET = "support/add-ticket"
    //</editor-fold>

    // <editor-fold desc="Purchase">
    const val GET_CATEGORIES = "categories/list-categories"
    const val GET_MERCHANTS = "merchants/list-merchants/{category_id}"
    const val PURCHASE = "purchase/purchase-product"
    const val PRINT = "purchase/mark-printed-transactions"
    //</editor-fold>

    //<editor-fold desc="RECHARGING">
    const val RESELLER_RECHARGE_METHODS = "charging-methods/get-charging-methods"
    const val CHARGING_LOG_METHODS = "charging-log/get-charging-log-methods"
    const val CHARGING_LOG_LIST = "charging-log/get-charging-log"
    const val GET_AMOUNT_AFTER = "charging-methods/get-amount"
    const val GET_CHECKOUT_ID = "charging-methods/get-checkout-id"
    const val GET_PAYMENT_STATUS = "charging-methods/get-payment-status"
    const val GET_PAYPAL_DENOMENATIONS = "charging-methods/get-paypal-denomenations"
    const val GET_PAYPAL_AMOUNT_AFTER = "charging-methods/get-paypal-amount"
    const val EXPORT_RECHARGE_LOG_LIST = "charging-log/export-charging-log"

    //surepay_purchase
    const val InitPurchaseCart = "partner-purchase/init-purchase-cart"

    const val InitPurchase = "partner-purchase/init-purchase"

    const val CompletePurchaseCart = "partner-purchase/complete-purchase-cart"

    const val CompletePurchase = "partner-purchase/complete-purchase"


    const val CheckLastTransaction = "payment/checkLastTransaction"

    const val SUREPAY_RESELLER_RECHARGE_METHODS =
        "/bitaqaty-business/partner-recharge/get-charging-methods"
    const val SUREPAY_CHARGING = "partner-recharge/complete-recharge"
    const val VALIDATE_SUREPAY_CHARGING =
        "/bitaqaty-business/partner-recharge/init-recharge"
    const val CHARGE_LOG_TRANSATIONS = "partner-recharge/get-charging-Log"
    const val CHARGE_LOG_TRANSACTIONS_SUMMARY =
        "partner-recharge/get-charging-Log-summary"
    const val TRANSACTION_DETAILS = "partner-recharge/get-transaction-details"


    //SETTLEMENT
    const val CREATE_SETTLEMENT_REQUEST = "partner-settlement/create-settelement-request"
    const val GET_SETTLEMENT_REQUEST = "partner-settlement/get-settelement-requests"

    const val GET_SETTLEMENT_REQUEST_DATA = "partner-settlement/get-settelement-request-data"


    const val GET_CHARGING_LOG_SUMMARY = "charging-log/get-charging-Log-summary"


    //</editor-fold>

    //<editor-fold desc="PRODUCT_DISCOUNT_LIST">
    const val GET_PRODUCT_LIST = "products/list-products"
    const val GET_CATEGORY_LIST = "lookups/list-categories"
    const val GET_MERCHANT_LIST = "lookups/list-merchants/{id}"
    const val EXPORT_PRODUCT_LIST = "products/export-products-list"
    //</editor-fold>

    //<editor-fold desc="TRANSACTION_LOG">
    const val GET_USERNAMES_LIST = "transaction-log/list-sub-accounts"
    const val GET_TRANSACTIONS_LIST = "transaction-log/list-transaction-log"
    const val EXPORT_TRANSACTIONS_LIST = "transaction-log/export-transaction-log"
    //</editor-fold>

    //<editor-fold desc="HOME_APIS">
    const val GET_DAILY_SALES = "reseller-home/list-daily-sales"
    const val GET_DAILY_RECHARGE = "reseller-home/list-daily-recharge"
    const val GET_SUB_ACCOUNTS_SALES = "reseller-home/list-sub-accounts-by-sales"
    const val GET_SELLER_HOME_BANK_REQUESTS = "reseller-home/bank-transfer-requests"
    const val SUB_ACCOUNT_HOME = "sub-acc-home/top-merchants"
    const val SUB_ACCOUNT_HOME_CHILD = "sub-acc-home/list-child-merchants"
    const val SUB_ACCOUNT_HOME_Edit_Top_Merchants = "sub-acc-home/edit-top-merchants"

    //  merchants/add-merchant/312243/89
    const val SUB_ACCOUNT_HOME_Add_Merchant = "merchants/add-merchant/{merchantId}/{categoryId}"
    const val SUB_ACCOUNT_HOME_Delete_Merchant = "merchants/delete-merchant/{merchantId}"

    //manage sub account
    const val SAVE_SUB_RESELLER_PRICES = "sub_reseller_prices/save-sub_reseller_prices"
    const val RESET_ALL_RECOMMENDED_PRICE = "recommended-price/reset-all-recommended-prices"
    const val SAVE_RECOMMENDED_PRICE = "recommended-price/save-recommended-prices"
    const val REST_RECOMMENDED_PRICE = "recommended-price/reset-recommended-price"
    const val BB_VERSION = "auth/get-BB-version"

    //</editor-fold>


    //shopping cart
    const val ADD_TO_CART = "shopping-cart/Add-To-Cart"
    const val GET_CART_PRODUCTS = "shopping-cart/get-cart-products"
    const val UPDATE_CART_PRODUCTS = "shopping-cart/update-cart-product-quantity"
    const val DELETE_CART_PRODUCT = "shopping-cart/delete-cart-product"
    const val PURCHASE_ORDER = "shopping-cart/purchase-order"
    const val EXPIRED_PRODUCT = "shopping-cart/get-expired-products"

    //favorite
    const val ADD_FAVORITE = "favorite-products/add-favorite-product"
    const val DELETE_FAVORITE = "favorite-products/delete-favorite-product"
    const val FAVORITE_PRODUCTS = "favorite-products/list-favorite-products"

}