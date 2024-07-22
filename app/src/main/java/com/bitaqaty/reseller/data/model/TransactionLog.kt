package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import java.io.Serializable

data class TransactionLog(
    var id: Int? = 0,
    var date: String? = null,
    var productNameEn: String? = null,
    var productNameAr: String? = null,
    var productSerial: String? = null,
    var productSecret: String? = null,
    var productPassword: String? = null,
    var productUserName: String? = null,
    var productSerialTitleEn: String? = null,
    var productSerialTitleAr: String? = null,
    var productSecretTitleEn: String? = null,
    var productSecretTitleAr: String? = null,
    var productUserNameTitleEn: String? = null,
    var productUserNameTitleAr: String? = null,
    var currencyAr: String? = null,
    var currencyEn: String? = null,
    var subReselleraccount: String? = null,
    val productDetailsAr: String? = null,
    val productDetailsEn: String? = null,
    val channelCode: String? = null,
    var total: Double = 0.0,
    val vatAmount: Double = 0.0,
    val price: Double = 0.0,
    val balanceAfter: Double? = null,
    val recommendedRetailPrice: Double? = null,
    val vatOnRecommendedPrice: Double? = null,
    val totalVat: Double? = null,
    val recommendedRetailPriceAfterVAT: Double? = null,
    val expectedProfit: String? = null,
    val channelId: Int = 0,
    var printCounter: String? = null,
    val showPin: Boolean = false,
    var transactionID: String? = null,
    val cancelled: Boolean = false,
    val printed: Boolean = false,
    var productType: String? = null,
    var barcode: String? = null,
    var showBarCode: Boolean? = false,
    var receiptPrintDetailsEn: String? = null,
    var receiptPrintDetailsAr: String? = null,
    var enableReceiptDetails: Boolean? = false,
    var isItunesMerchant: Boolean? = false,
    var transactionRefNumber: String? = null,
    var subTransactionId: String? = null,
    var subTransactionDate: String? = null,
    var subTransactionRefNumber: String? = null,
    var subTransactionVatValue: String? = null,
    var subTransactionActualProfit: String? = null,
    var subTransactionPrice: String? = null,
    var subTransactionCost: String? = null,
    var subTransactionBalanceAfter: String? = null,
    var subTransactionAmount: String? = null,
    var transactionUUID: String? = null,
    var subTransactionPrintCounter: String? = null,
    var subTransactionExpectedProfit: String? = null,
    var paymentMethodEn: String? = null,
    var paymentMethodAr: String? = null,
    var itemExpirationDate: String? = null,
    var merchantId: String? = null,
    var merchantLogoPath: String? = null,
    var showSkuBarcode: Boolean? = false,
    var skuBarcode: String? = null,
    var vatCode: String? = null,
    var vatPercentage: String? = null)
//) : Serializable {
//
//    fun getPaymentMethod(lang: String): String {
//        return if (lang == "en") {
//            paymentMethodEn ?: ""
//        } else {
//            paymentMethodAr ?: ""
//        }
//    }
//
//    fun getMerchantLogo(): String {
//        return merchantLogoPath ?: ""
//    }
//
//    fun getMerchant(): String {
//        return merchantId ?: ""
//    }
//
//    fun getRecommendedRetailPrice(): String {
//        return if (recommendedRetailPrice == null || recommendedRetailPrice == 0.0) {
//            "N/A"
//        } else {
//            "${recommendedRetailPrice} ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun expectedProfit(): String {
//        return if (expectedProfit == null) {
//            "N/A"
//        } else {
//            "$expectedProfit ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun vatAmount(): String {
//        return if (vatAmount == 0.0) {
//            "N/A"
//        } else {
//            "$vatAmount ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun total(): String {
//        return if (total == 0.0) {
//            "N/A"
//        } else {
//            "$total ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun price(): String {
//        return if (price == 0.0) {
//            "N/A"
//        } else {
//            "$price ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun balanceAfter(): String {
//        return if (balanceAfter == null) {
//            "N/A"
//        } else {
//            "$balanceAfter ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionVatValue(): String {
//        return if (subTransactionVatValue == null) {
//            "N/A"
//        } else {
//            "$subTransactionVatValue ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionAmount(): String {
//        return if (subTransactionAmount == null) {
//            "N/A"
//        } else {
//            "$subTransactionAmount ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionPrice(): String {
//        return if (subTransactionPrice == null) {
//            "N/A"
//        } else {
//            "$subTransactionPrice ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionExpectedProfit(): String {
//        return if (subTransactionExpectedProfit == null) {
//            "N/A"
//        } else {
//            "$subTransactionExpectedProfit ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionBalanceAfter(): String {
//        return if (subTransactionBalanceAfter == null) {
//            "N/A"
//        } else {
//            "$subTransactionBalanceAfter ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun subTransactionActualProfit(): String {
//        return if (subTransactionActualProfit == null) {
//            "N/A"
//        } else {
//            "$subTransactionActualProfit ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun getVatOnRecommendedRetailPrice(): String {
//        return if (vatOnRecommendedPrice == null) {
//            "N/A"
//        } else {
//            "${vatOnRecommendedPrice}  ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun getTotalVat(): String {
//        return if (totalVat == null) {
//            "N/A"
//        } else {
//            "${totalVat}  ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun getRecommendedRetailPriceAfterVAT(): String {
//        return if (recommendedRetailPriceAfterVAT == null || recommendedRetailPriceAfterVAT == 0.0) {
//            "N/A"
//        } else {
//            "${recommendedRetailPriceAfterVAT}  ${Utils.getUserCurrency()}"
//        }
//    }
//
//    fun getProductName(lang: String): String {
//        return if (lang == "en") {
//            productNameEn ?: ""
//        } else {
//            productNameAr ?: ""
//        }
//    }
//
//    fun getProductSerialTitle(lang: String): String {
//        return if (lang == "en") {
//            productSerialTitleEn ?: ""
//        } else {
//            productSerialTitleAr ?: ""
//        }
//    }
//
//    fun getProductSerialTitleEn(): String {
//        return productSerialTitleEn ?: ""
//    }
//
//    fun getProductSerialTitleAr(): String {
//        return productSerialTitleAr ?: ""
//    }
//
//    fun getProductSecretTitle(lang: String): String {
//        return if (lang == "en") {
//            productSecretTitleEn ?: ""
//        } else {
//            productSecretTitleAr ?: ""
//        }
//    }
//
//    fun getProductUserNameTitle(lang: String): String {
//        return if (lang == "en") {
//            productUserNameTitleEn ?: ""
//        } else {
//            productUserNameTitleAr ?: ""
//        }
//    }
//
//    fun getProductUserNameTitleAr(): String {
//        return productUserNameTitleAr ?: ""
//    }
//
//    fun getProductUserNameTitleEn(): String {
//        return productUserNameTitleEn ?: ""
//    }
//
//    fun getProductSecretTitleEn(): String {
//        return productSecretTitleEn ?: ""
//    }
//
//    fun getProductSecretTitleAr(): String {
//        return productSecretTitleAr ?: ""
//    }
//
//    fun getProductDetails(lang: String): String {
//        return if (lang == "en") {
//            productDetailsEn ?: ""
//        } else {
//            productDetailsAr ?: ""
//        }
//    }
//
//    fun getProductSecret(): String {
//        if (productSecret.isNullOrBlank()) {
//            return productPassword ?: ""
//        }
//        return productSecret ?: productPassword ?: ""
//    }
//
//    fun getDate(): String {
//        return if (date?.isEmpty() == true) "" else date!!.replace(",", "")
//    }
//
//    fun getProductUserName(): String {
//        return productUserName ?: ""
//    }
//
//    fun getCurrency(lang: String): String {
//        return if (lang == "en") {
//            currencyEn ?: ""
//        } else {
//            currencyAr ?: ""
//        }
//    }
//
//    fun getTransactionId(): String {
//        return transactionID ?: ""
//    }
//
//    fun getProductSerial(): String {
//        return productSerial ?: ""
//    }
//
//    fun getSubAccount(): String {
//        return subReselleraccount ?: ""
//    }
//
//    fun getSecretOrNull(): String? {
//        return productSecret
//    }
//
//    fun getDetails(): String {
//        return if (lang == "en") {
//            receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
//        } else {
//            receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
//        }
//    }
//
//    fun getDetailsEn(): String {
//        return receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
//    }
//
//    fun getDetailsAr(): String {
//        return receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
//    }
//
//    fun getPrintCounter(): String {
//        return printCounter ?: ""
//    }
//
//    fun getPrintIntCounter(): Int {
//        return printCounter?.toIntOrNull() ?: 0
//    }
//
//    fun getSubTransactionBalanceAfter(): String {
//        return if (subTransactionBalanceAfter == null) {
//            "N/A"
//        } else {
//            "${(subTransactionBalanceAfter?.fmt())} ${Utils.getUserCurrency()}"
//        }
//    }
//
//
//    fun increaseCounter() {
//        printCounter?.toIntOrNull()?.let {
//            printCounter = "${it + 1}"
//        } ?: run {
//            printCounter = "1"
//        }
//    }
//
//    fun setAsPrinted() {
//        printCounter = "1"
//    }
//
//    fun getTotalPrice(): String {
//        return "${total.fmt()} ${Utils.getUserCurrency()}"
//    }
//}