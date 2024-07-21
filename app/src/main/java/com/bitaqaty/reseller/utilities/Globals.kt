package com.bitaqaty.reseller.utilities


import com.bitaqaty.reseller.data.model.SystemSettings

import com.bitaqaty.reseller.BuildConfig
object Globals {
    private const val TAG = "Globals"
    const val PAGE_SIZE = 10
    const val LOG_PAGE_SIZE = 20
    const val STORE_KEY_ALIAS = "onecard"
    const val STORE_KEY_PASSWORD = "OneCard\$160314"
    const val APPLICATION_ID = "com.bitaqaty.reseller"
    var DEV_ID = ""


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

    //<editor-fold desc="Constants">
    const val PASS_REGEX = "[A-Za-z0-9!#\$%&(){|}~:;<=>?@*+,./\\]\\[^_`\\'\\\" \\t\\r\\n\\f-]+"
    const val CURRENT_LANG = "CURRENT_LANG"
    const val LOGIN_PROCESS_TOKEN = "LOGIN_PROCESS_TOKEN"
    const val RESET_ACCESS_TOKEN = "RESET_ACCESS_TOKEN"
    const val RESELLER_Profit_TYPE = "RESELLER_Profit_TYPE"
    const val USER_NAME = "USER_NAME"
    const val EMAIL = "EMAIL"
    const val TYPE = "TYPE"
    const val CHANGE_PASSWORD_TYPE = "CHANGE_PASSWORD_TYPE"
    const val RESET_PASSWORD_TOKEN = "RESET_PASSWORD_TOKEN"
    const val MORE_EXTRA_TYPE = "MORE_EXTRA_TYPE"
    const val SHARED_PREFS = ".pref_file"
    const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
    const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
    const val USER_DATA = "USER_DATA"
    const val IS_MADA = "IS_MADA"
    const val CATEGORY_DATA = "CATEGORY_DATA"
    const val MERCHANT_DATA = "MERCHANT_DATA"
    const val IS_TOP_MERCHANT = "IS_TOP_MERCHANT"
    const val PRODUCT_DATA_CART = "PRODUCT_DATA"
    const val PRODUCT_DATA = "PRODUCT_DATA"
    const val PRODUCT_DETAILS_DATA = "PRODUCT_DETAILS_DATA"
    const val CART_PRODUCTS_DETAILS_DATA = "CART_PRODUCTS_DETAILS_DATA"
    const val DEVICE_ID = "DEVICE_ID"
    const val ERROR_LOG = "ERROR_LOG"
    const val NEW_LEVEL = "NEW_LEVEL"
    const val IS_HOME = "IS_HOME"
    const val CREDIT_DATA = "CREDIT_DATA"
    const val AMEX_DATA = "AMEX_DATA"
    const val MADA_DATA = "MADA_DATA"
    const val TOKEN_EXPIRED = "TOKEN_EXPIRED"
    const val MADA_VERSION = "MADA_VERSION"
    const val MADA_VERSION_API = "MADA_VERSION_API"
    const val M_N_C = "MNC"
    const val IS_MOBILY = "IS_MOBILY"
    const val MAX_COUNT = 50

    //shopping cart
    const val ADD_TO_CART = "shopping-cart/Add-To-Cart"
    const val GET_CART_PRODUCTS = "shopping-cart/get-cart-products"
    const val UPDATE_CART_PRODUCTS = "shopping-cart/update-cart-product-quantity"
    const val DELETE_CART_PRODUCT = "shopping-cart/delete-cart-product"
    const val PURCHASE_ORDER = "shopping-cart/purchase-order"
    const val EXPIRED_PRODUCT = "shopping-cart/get-expired-products"

    // const val DOMAIN = "whitelabel.ocstaging.net"
   const val DOMAIN = BuildConfig.DOMAIN
    //  const val DOMAIN = "bitaqatybusiness.ocstaging.net"
    //  const val DOMAIN = "bitaqatybusiness.com"

    //</editor-fold>

    //<editor-fold desc="Global values">
    var lang: String = "en"
    var MORE_PERMISSIONS = intArrayOf()
    var CURRENT_INDEX = 0
    var CURRENT_HOME_SUBE_SELECTED = DATE.CURRENT_MONTH.value
    var CURRENT_HOME_REPORT_SELECTED = DATE.CURRENT_MONTH.value
    var DATE_FROM = ""
    var DATE_TO = ""
    var WhitelabelCode = ""
    const val INCREASE = "increase"
    const val DECREASE = "decrease"

    var IS_CENTER_SELECTED = false
    var SETTINGS = ArrayList<SystemSettings>()
    //</editor-fold>

    //<editor-fold desc="Enums">

    //<editor-fold desc="Local Enums">
    enum class AlertType(val value: Int) {
        One(0),
        Two(1),
        Three(2);
    }

    enum class HeaderTypes(val value: Int) {
        None(0),
        logout(1),
        Notification(2);
    }

    enum class ValidationType(val value: Int) {
        default(0),
        valid(1),
        invalid(2);
    }

    enum class IconType(val value: Int) {
        None(0),
        Success(1),
        Warning(2);
    }

    enum class ChangePasswordType(val value: Int) {
        FirstLogin(0),
        Default(1);
    }

    enum class ExtraType(val value: Int) {
        Terms(0),
        FAQ(1),
        Privacy(2);
    }

    enum class Role(val value: String) {
        Reseller("MASTER_ACCOUNT"),
        SubAccount("SUB_ACCOUNT");
    }
    //</editor-fold>

    //<editor-fold desc="Api Enums">

    enum class ERROR_CODES(val value: String) {
        NO_RECHARGE_PERMISSION("57"),
        NUMBER_OF_REQUESTS_PER_DAY("58"),
        TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("59"),
        MIN_AMOUNT_PER_REQUESTS("60"),
        MAX_AMOUNT_PER_REQUESTS("61"),
        MAX_REQUEST_PER_DAY("62"),
        ERROR_RECHARGE_LOG_PERMISSION("96"),
        NO_PERMISSION_FOR_TRANSACTION_LOG("104"),
        NO_PERMISSION_FOR_PRODUCT_LIST("999"),
        INVALID_RECOMMENDED_PRICE("150"),
        MOBILE_NUMBER_ALREADY_EXIST("121"),
        USERNAME_ALREADY_EXIST("120")
    }

    //</editor-fold>

    //</editor-fold>
    enum class SlideDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    enum class SlideType {
        SHOW,
        HIDE
    }

    enum class SubAccountType(val value: String) {
        LIMIT("LIMIT"),
        NO_LIMIT("NO_LIMIT"),
        PERIODICAL_LIMIT("PERIODICAL_LIMIT")
    }

    enum class RECHARGE_TYPE(val value: Int) {
        BANK(0),
        MADA(1),
        CREDIT(2),
        AMEX(3),
        PAYPAL(4),
        MADA_AHLY(5)
    }

    enum class SUB_DURATION_TYPE(val value: String) {
        DAILY("DAILY"),
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY")
    }

    enum class ACCESS_TYPE(val value: String) {
        DEFAULT("DEFAULT"),
        WEBSITE("WEBSITE"),
        APPLICATION("APPLICATION")
    }

    enum class RESELLER_TYPE(val value: String) {
        BALANCE("BALANCE"),
        LIMIT("LIMIT")
    }

    enum class ACCOUNT_TYPE(val value: String) {
        SUBACCOUNT("SUB_ACCOUNT"),
        MASTERACCOUNT("MASTER_ACCOUNT")
    }

    enum class PERMISSIONS_IDS(val value: Int) {
        PURCHASE(1),
        VIEW_PURCHASE_LIMIT(2),
        VIEW_MASTER_BALANCE(3),
        VIEW_PRODUCT_DISCOUNT(4),
        RECHARGE(5),
        VIEW_TRANSACTION_LOG(6),
        VIEW_PRODUCT_LIST(7),
        VIEW_REPORTS(8),
        VIEW_SUPPORT_CENTER(9),
        RECOMMENDED_RETAIL_PRICE(25),
        EDIT_HOME_PAGE(26),
        CAN_SEND_SETTLEMENT(31),
        VIEW_PIN(24)
    }

    enum class CHILD_RECHARGE_PERMISSIONS_IDS(val value: Int) {
        RECHARGE_LOG(10),
        CHOOSE_RECHARGE_LOG(11),
        BANK(12),
        MADA(13),
        CREDIT(14),
        AMEX(15),
        PAYPAL(16)
    }

    enum class CHILD_TRANSACTION_PERMISSIONS_IDS(val value: Int) {
        CHOOSE_TRANSACTION_LOG(17),
        ACCOUNT_ONLY(18),
        ALL_SUB_ACCOUNTS(19),
        REPRINT_RECEIPT(23),
        VIEW_PIN(24),
    }

    enum class CHILD_RECOMMENDED_RETAIL_PRICE_IDS(val value: Int) {
        VIEW_PROFIT(30)
    }



    enum class CHILD_REPORTS_PERMISSIONS_IDS(val value: Int) {
        CHOOSE_REPORTS(20),
        ACCOUNT_ONLY(21),
        ALL_ACCOUNTS(22),
        VIEW_CURRENT_BALANCE(29)
    }

    enum class ErrorType(val value: Int) {
        Network(0),
        Server(1),
        Unknown(2),
        Empty(3),
        Auth(4),
        Balance(133),
        NOPermission(5),
        INSUFFICIENTRESELLERBALANCE(62);
    }

    enum class SUBACCOUNT_TABS(val value: Int) {
        HOME(1),//
        STORE(2),//
        RECHARGE(3),//
        TRANSACTION_LOG(4),
        REPORTS(5),
        SUPPORT(6),
        MORE(7),//
        PRODUCT_LIST(8),
        SHOPPING_CART(9)
    }

    enum class BankTransferStatus(val value: String) {
        ALL("all"),
        PENDING("pending"),
        ACCEPTED("accepted"),
        REJECTED("rejected");
    }

    enum class RECHARGE_METHODS(val value: String, val typeF: Int) {
        CREDIT_CARD("CREDIT_CARD", 2),
        PAYPAL("PAYPAL", 4),
        AMEX("AMEX", 3),
        BANK_TRANSFER("BANK_TRANSFER", 0),
        MADA_AHLI("MADA_AHLI", 5),
        MADA("MADA", 1),
        MADA_SALES_COMMISSION("MADA_SALES_COMMISSION", 6);

        private var code: String? = null
        private var type: Int? = 0

        private fun RECHARGE_METHODS(code: String, type: Int) {
            this.code = code
            this.type = type
        }

        open fun getCode(): String? {
            return code
        }

        open fun getType(): Int? {
            return type
        }

        open fun getCodeByType(type: Int): String? {
            return value
        }
    }

    enum class SETTING_KEYS(val value: String) {
        SEARCH_SUBACCOUNT_KEYWORD_MIN_LENGTH("SEARCH_SUBACCOUNT_KEYWORD_MIN_LENGTH"),
        CREDIT_CARD_NUMBER_OF_REQUESTS_PER_DAY("CREDIT_CARD_NUMBER_OF_REQUESTS_PER_DAY"),
        CREDIT_CARD_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("CREDIT_CARD_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY"),
        CREDIT_CARD_MAXIMUM_AMOUNT_PER_REQUEST("CREDIT_CARD_MAXIMUM_AMOUNT_PER_REQUEST"),
        CREDIT_CARD_MINIMUM_AMOUNT_PER_REQUEST("CREDIT_CARD_MINIMUM_AMOUNT_PER_REQUEST"),
        BANK_TRANSFER_NUMBER_OF_REQUESTS_PER_DAY("BANK_TRANSFER_NUMBER_OF_REQUESTS_PER_DAY"),
        BANK_TRANSFER_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("BANK_TRANSFER_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY"),
        BANK_TRANSFER_MAXIMUM_AMOUNT_PER_REQUEST("BANK_TRANSFER_MAXIMUM_AMOUNT_PER_REQUEST"),
        BANK_TRANSFER_MINIMUM_AMOUNT_PER_REQUEST("BANK_TRANSFER_MINIMUM_AMOUNT_PER_REQUEST"),
        MADA_NUMBER_OF_REQUESTS_PER_DAY("MADA_NUMBER_OF_REQUESTS_PER_DAY"),
        MADA_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("MADA_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY"),
        MADA_MAXIMUM_AMOUNT_PER_REQUEST("MADA_MAXIMUM_AMOUNT_PER_REQUEST"),
        MADA_MINIMUM_AMOUNT_PER_REQUEST("MADA_MINIMUM_AMOUNT_PER_REQUEST"),

        SUREPAY_MADA_NUMBER_OF_REQUESTS_PER_DAY("SUREPAY_MADA_ELAHLI_MAXIMUM_NUMBER_OF_REQUESTS_PER_DAY"),
        SUREPAY_MADA_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("SUREPAY_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_DAY"),
        SUREPAY_MADA_MAXIMUM_AMOUNT_PER_REQUEST("SUREPAY_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_REQUEST"),
        SUREPAY_MADA_MINIMUM_AMOUNT_PER_REQUEST("SUREPAY_MADA_ELAHLI_MINIMUM_AMOUNT_PER_REQUEST"),


        CACHIN_MADA_NUMBER_OF_REQUESTS_PER_DAY("CACHIN_MADA_ELAHLI_MAXIMUM_NUMBER_OF_REQUESTS_PER_DAY"),
        CACHIN_MADA_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("CACHIN_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_DAY"),
        CACHIN_MADA_MAXIMUM_AMOUNT_PER_REQUEST("CACHIN_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_REQUEST"),
        CACHIN_MADA_MINIMUM_AMOUNT_PER_REQUEST("CACHIN_MADA_ELAHLI_MINIMUM_AMOUNT_PER_REQUEST"),


        NEARPAY_MADA_NUMBER_OF_REQUESTS_PER_DAY("NEARPAY_MADA_ELAHLI_MAXIMUM_NUMBER_OF_REQUESTS_PER_DAY"),
        NEARPAY_MADA_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("NEARPAY_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_DAY"),
        NEARPAY_MADA_MAXIMUM_AMOUNT_PER_REQUEST("NEARPAY_MADA_ELAHLI_MAXIMUM_AMOUNT_PER_REQUEST"),
        NEARPAY_MADA_MINIMUM_AMOUNT_PER_REQUEST("NEARPAY_MADA_ELAHLI_MINIMUM_AMOUNT_PER_REQUEST"),



        AMEX_NUMBER_OF_REQUESTS_PER_DAY("AMEX_NUMBER_OF_REQUESTS_PER_DAY"),
        AMEX_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("AMEX_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY"),
        AMEX_MAXIMUM_AMOUNT_PER_REQUEST("AMEX_MAXIMUM_AMOUNT_PER_REQUEST"),
        AMEX_MINIMUM_AMOUNT_PER_REQUEST("AMEX_MINIMUM_AMOUNT_PER_REQUEST"),
        PAYPAL_NUMBER_OF_REQUESTS_PER_DAY("PAYPAL_NUMBER_OF_REQUESTS_PER_DAY"),
        PAYPAL_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY("PAYPAL_TOTAL_AMOUNT_OF_REQUESTS_PER_DAY"),
        HYPERPAY_SAVE_CARD_INFO("HYPERPAY_SAVE_CARD_INFO"),
        MINIMUM_NUMBER_OF_HOME_MERCHANTS("MINIMUM_NUMBER_OF_HOME_MERCHANTS"),
        MAXIMUM_NUMBER_OF_HOME_MERCHANTS("MAXIMUM_NUMBER_OF_HOME_MERCHANTS"),
        NOTIFICATIONS_MAX_COUNT("NOTIFICATIONS_MAX_COUNT");
    }

    enum class CARD_BRAND(val value: String) {
        VISA("VISA"),
        MASTER("MASTER"),
        MADA("MADA"),
        PAYPAL("PAYPAL"),
        AMEX("AMEX");
    }

    enum class ProductType(val value: String) {
        Serial("SERIAL"),
        Credential("CREDINTIAL"),
        Service("SERVICE"),
        Predefined("PRICED_VOUCHER");
        //ONFLY_VOUCHER
        //PRICED_VOUCHER
    }

    enum class CHANNEL(val value: String) {
        POS("POS"),
        PORTAL("PORTAL"),
        INTEGRATION("INTEGRATION"),
        WALLET("WALLET");
    }

    enum class DATE(val value: String) {
        CURRENT_MONTH("CURRENT_MONTH"),
        LAST_MONTH("LAST_MONTH"),
        LAST_SEVEN_DAYS("LAST_SEVEN_DAYS"),
        YESTERDAY("YESTERDAY"),
        TODAY("TODAY");
    }
}