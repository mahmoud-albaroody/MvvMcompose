package com.bitaqaty.reseller.data.model


import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import java.io.Serializable

class ProductDetails(err: ErrorMessage) : Serializable {
    var errors: ArrayList<ErrorMessage>? = ArrayList()
    var products: ArrayList<TransactionLog>? = null
    var vatAmount: Double = 0.0
    var totalVatAmount: String? = null
    var oneItemPriceBeforeVat: Double = 0.0
    var totalItemsPriceBeforeVat: String? = null
    var oneItemPriceAfterVat: Double = 0.0
    var totalItemsPriceAfterVat: String? = null
    var recommendedRetailPrice: String? = null
    var totalRecommendedRetailPrice: String? = null
    var totalRecommendedRetailPriceAfterVat: String? = null
    var itemsCount: Int = 0
    var purchaseDate: String = ""
    var purchaseDateTime: String? = null
    var vatPercentage: String? = null
    var productId: Int = 0
    var subResellerPrice: String? = null
    var subResellerPriceAfterVat: String? = null
    var subResellerPriceVatAmount: String? = null
    var totalSubResellerPrice: String? = null
    var totalSubResellerPriceAfterVat: String? = null
    var totalSubResellerPriceVatAmount: String? = null
    var showSKUBarcode: Boolean? = false
    var skuBarcode: String? = null
    var resellerCommission: String? = null
    var surepayCommission: String? = null
    var oneItemPriceAfterCommission: String? = null
    var totalItemsPriceAfterCommission: String? = null
    var vatCode: String? = null
    var merchantId: String? = null
    var merchantLogo:String = ""
    val paymentMethodEn: String? = null
    val productNameEn: String = ""
    val productNameAr: String = ""



    init {
        errors = arrayListOf(err)
    }

    fun getProductName(): String {
        return if (Utils.getLang() == "ar") {
            productNameAr
        } else {
            productNameEn
        }
    }

    fun getPriceWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${subResellerPrice ?: ""} ${Utils.getUserCurrency()}"
        else
            "${oneItemPriceBeforeVat ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalPriceWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPrice ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalItemsPriceBeforeVat ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getVatWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPriceVatAmount ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalVatAmount ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalAfterVatWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalItemsPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }


    fun getRecommendedRetailPriceWithCurrency(): String {
        return "${recommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailPriceWithCurrency(): String {
        return "${totalRecommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailAfterVatWithCurrency(): String {
        return "${totalRecommendedRetailPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }
}

class ProductData : Serializable {
    var id: Int = 0
    var productSerial: String = ""
    var productUsername: String? = null
    var productPassword: String? = null
    var productSecret: String? = null
    var transRefNumber: String? = null
    var productUsernameTitleEn: String = ""
    var productSerialTitleEn: String = ""
    var productSecretTitleEn: String = ""
    var productUsernameTitleAr: String = ""
    var productSerialTitleAr: String = ""
    var productSecretTitleAr: String = ""
    var barcode: String? = null
    var receiptPrintDetailsEn: String? = null
    var receiptPrintDetailsAr: String? = null
    var enableReceiptDetails: Boolean? = false
    var isItunesMerchant: Boolean? = false
    var showBarCode: Boolean? = null
    var itemExpirationDate: String? = null
    var merchantLogo: String? = null
    var merchantId: String? = null
    var showSkuBarcode: Boolean? = false
    var skuBarcode: String? = null
    var isServiceCredential: Boolean = false

    fun getUserNameTitle(): String {
        return if (lang == "en") {
            productUsernameTitleEn
        } else {
            productUsernameTitleAr
        }
    }

    fun getUserSecretTitle(): String {
        return if (lang == "en") {
            productSecretTitleEn
        } else {
            productSecretTitleAr
        }
    }

    fun getSerialTitle(): String {
        return if (lang == "en") {
            productSerialTitleEn
        } else {
            productSerialTitleAr
        }
    }

    fun getSerialTitleEn(): String {
        return productSerialTitleEn
    }

    fun getSerialTitleAr(): String {
        return productSerialTitleAr
    }

    fun getDetails(): String {
        return if (lang == "en") {
            receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
        } else {
            receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
        }
    }


    fun getDetailsEn(): String {
        return receiptPrintDetailsEn ?: ""
    }

    fun getDetailsAr(): String {
        return receiptPrintDetailsAr ?: ""
    }
}