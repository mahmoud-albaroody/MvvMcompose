package com.bitaqaty.reseller.data.model


import android.view.View
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import java.io.Serializable

data class TransactionLog(
    val id: Int? = 0,
    val date: String? = null,
    val productNameEn: String? = null,
    val productNameAr: String? = null,
    val productSerial: String = "",
    val productSecret: String = "",
    val productPassword: String? = null,
    val productUserName: String? = "",
    val productSerialTitleEn: String? = null,
    val productSerialTitleAr: String? = null,
    val productSecretTitleEn: String? = null,
    val productSecretTitleAr: String? = null,
    val productUserNameTitleEn: String? = null,
    val productUserNameTitleAr: String? = null,
    val currencyAr: String? = null,
    val currencyEn: String? = null,
    val subReselleraccount: String = "",
    val productDetailsAr: String? = null,
    val productDetailsEn: String? = null,
    val channelCode: String? = null,
    val total: Double = 0.0,
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
    val transactionID: String? = null,
    val cancelled: Boolean = false,
    val printed: Boolean = false,
    val productType: String? = null,
    val barcode: String? = null,
    val showBarCode: Boolean? = false,
    val receiptPrintDetailsEn: String? = null,
    val receiptPrintDetailsAr: String? = null,
    val enableReceiptDetails: Boolean? = false,
    val isItunesMerchant: Boolean? = false,
    val transactionRefNumber: String? = null,
    val subTransactionId: String? = null,
    val subTransactionDate: String? = null,
    val subTransactionRefNumber: String? = null,
    val subTransactionVatValue: String? = null,
    val subTransactionActualProfit: String? = null,
    val subTransactionPrice: String? = null,
    val subTransactionCost: String? = null,
    val subTransactionBalanceAfter: String? = null,
    val subTransactionAmount: String? = null,
    val transactionUUID: String? = null,
    val subTransactionPrintCounter: String? = null,
    val subTransactionExpectedProfit: String? = null,
    val paymentMethodEn: String? = null,
    val paymentMethodAr: String? = null,
    val itemExpirationDate: String? = null,
    val merchantId: String? = null,
    val merchantLogoPath: String? = null,
    val showSkuBarcode: Boolean? = false,
    val skuBarcode: String? = null,
    val vatCode: String? = null,
    val vatPercentage: String? = null,
    var visible: Boolean
) : Serializable {
    fun getMerchant(): String {
        return merchantId ?: ""
    }

    fun getMerchantLogo(): String {
        return merchantLogoPath ?: ""
    }

    fun getPaymentMethod(): String {
        return if (lang == "en") {
            paymentMethodEn ?: ""
        } else {
            paymentMethodAr ?: ""
        }
    }

    fun getProductSeriall(): String {
        return productSerial ?: ""
    }

    fun getProductSecrett(): String {
        if (productSecret.isNullOrBlank()) {
            return productPassword ?: ""
        }
        return productSecret ?: productPassword ?: ""
    }

    fun getCheckingRecommendedRetailPrice(): String {
        return if (recommendedRetailPrice == null || recommendedRetailPrice == 0.0) {
            "N/A"
        } else {
            "$recommendedRetailPrice ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingExpectedProfit(): String {
        return if (expectedProfit == null) {
            "N/A"
        } else {
            "$expectedProfit ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingVatAmount(): String {
        return if (vatAmount == 0.0) {
            "N/A"
        } else {
            "$vatAmount ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingTotal(): String {
        return if (total == 0.0) {
            "N/A"
        } else {
            "$total ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingPrice(): String {
        return if (price == 0.0) {
            "N/A"
        } else {
            "$price ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingBalanceAfter(): String {
        return if (balanceAfter == null) {
            "N/A"
        } else {
            "$balanceAfter ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingSubTransactionVatValue(): String {
        return if (subTransactionVatValue == null) {
            "N/A"
        } else {
            "$subTransactionVatValue ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingSubTransactionPrice(): String {
        return if (subTransactionPrice == null) {
            "N/A"
        } else {
            "$subTransactionPrice ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingSubTransactionAmount(): String {
        return if (subTransactionAmount == null) {
            "N/A"
        } else {
            "$subTransactionAmount ${Utils.getUserCurrency()}"
        }
    }


    fun getCheckingSubTransactionExpectedProfit(): String {
        return if (subTransactionExpectedProfit == null) {
            "N/A"
        } else {
            "$subTransactionExpectedProfit ${Utils.getUserCurrency()}"
        }
    }


    fun getCheckingSubTransactionActualProfit(): String {
        return if (subTransactionActualProfit == null) {
            "N/A"
        } else {
            "$subTransactionActualProfit ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingVatOnRecommendedRetailPrice(): String {
        return if (vatOnRecommendedPrice == null) {
            "N/A"
        } else {
            "${vatOnRecommendedPrice}  ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingTotalVat(): String {
        return if (totalVat == null) {
            "N/A"
        } else {
            "${totalVat}  ${Utils.getUserCurrency()}"
        }
    }

    fun getCheckingRecommendedRetailPriceAfterVAT(): String {
        return if (recommendedRetailPriceAfterVAT == null || recommendedRetailPriceAfterVAT == 0.0) {
            "N/A"
        } else {
            "$recommendedRetailPriceAfterVAT  ${Utils.getUserCurrency()}"
        }
    }

    fun getProductName(): String {
        return if (lang == "en") {
            productNameEn ?: ""
        } else {
            productNameAr ?: ""
        }
    }

    fun getProductSerialTitle(): String {
        return if (lang == "en") {
            productSerialTitleEn ?: ""
        } else {
            productSerialTitleAr ?: ""
        }
    }


    fun getProductSecretTitle(): String {
        return if (lang == "en") {
            productSecretTitleEn ?: ""
        } else {
            productSecretTitleAr ?: ""
        }
    }

    fun getProductUserNameTitle(): String {
        return if (lang == "en") {
            productUserNameTitleEn ?: ""
        } else {
            productUserNameTitleAr ?: ""
        }
    }


    fun getProductDetails(): String {
        return if (lang == "en") {
            productDetailsEn ?: ""
        } else {
            productDetailsAr ?: ""
        }
    }

    fun getCheckingProductSecret(): String {
        if (productSecret.isNullOrBlank()) {
            return productPassword ?: ""
        }
        return productSecret
    }

    fun getCheckingDate(): String {
        return if (date?.isEmpty() == true) "" else date!!.replace(",", "")
    }


    fun getCurrency(): String {
        return if (lang == "en") {
            currencyEn ?: ""
        } else {
            currencyAr ?: ""
        }
    }


    fun getDetails(): String {
        return if (lang == "en") {
            receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
        } else {
            receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
        }
    }

    fun getDetailsEn(): String {
        return receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
    }

    fun getDetailsAr(): String {
        return receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
    }


    fun getPrintIntCounter(): Int {
        return printCounter?.toIntOrNull() ?: 0
    }

    fun getCheckingSubTransactionBalanceAfter(): String {
        return if (subTransactionBalanceAfter == null) {
            "N/A"
        } else {
            "${(subTransactionBalanceAfter.fmt())} ${Utils.getUserCurrency()}"
        }
    }

    fun getIncreaseCounter() {
        printCounter?.toIntOrNull()?.let {
            printCounter = "${it + 1}"
        } ?: run {
            printCounter = "1"
        }
    }

    fun getSetAsPrinted() {
        printCounter = "1"
    }

    fun getTotalPrice(): String {
        return "${total.fmt()} ${Utils.getUserCurrency()}"
    }
}