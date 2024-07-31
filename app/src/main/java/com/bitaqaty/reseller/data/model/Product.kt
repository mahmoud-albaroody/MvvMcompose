package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt

data class Product(
    var productId: Int = 0,
    var costPrice: String? = null,
    var costPriceAfterVat: String? = null,
    private var recommendedPrice: String? = null,
    var recommendedPriceAfterVat: String? = null,
    var nameAr: String? = null,
    var nameEn: String? = null,
    var descriptionAr: String? = null,
    var descriptionEn: String? = null,
    var availableStockAmount: Int = 0,
    private var price: String? = null,
    private var fullPrice: String? = null,
    private var vat: String? = null,
    private var fullVat: String? = null,
    private var priceAfterVat: String? = null,
    private var fullPriceAfterVat: String? = null,
    var vatePercentage: String? = null,
    var merchantNameAr: String? = null,
    var merchantNameEn: String? = null,
    var productSmallImagePath: String? = null,
    var bulkMin: Int = 0,
    var bulkMax: Int = 0,
    var productType: String? = null,
    var bulkPurchaseEnabled: Boolean = false,
    var bulkServicePurchaseEnabled: Boolean = false,
    var recommendedRetailPrice: String? = null,
    var vatOnRecommendedPrice: String? = null,
    var totalVat: String? = null,
    var recommendedRetailPriceAfterVat: String? = null,
    var subResellerPrice: String? = null,
    var subResellerPriceAfterVat: String? = null,
    var fullSubResellerPrice: String? = null,
    var fullSubResellerPriceAfterVat: String? = null,
    var skuCode: String? = null,
    var vatCode: String? = null,
    var merchantId: String? = null,
    var actualProfit: String? = null,
    var expectedProfit: String? = null,
    var haveValue: Boolean = true,
    var merchantLogoPath: String? = null,
    var isEditable: Boolean = false,
    var isHighlighting: Boolean = false,
    var isOpen: Boolean = false,
    var endUserPriceEnabled: Boolean = false,
    var isAddedToCart: Boolean = false,
    var quantity: Int = 1,
    var id: Int = 0,
    var productSerial: String = "",
    var productUsername: String? = null,
    var productPassword: String? = null,
    var productSecret: String? = null,
    var transRefNumber: String? = null,
    var productUsernameTitleEn: String = "",
    var productSerialTitleEn: String = "",
    var productSecretTitleEn: String = "",
    var productUsernameTitleAr: String = "",
    var productSerialTitleAr: String = "",
    var productSecretTitleAr: String = "",
    var barcode: String? = null,
    var receiptPrintDetailsEn: String? = null,
    var receiptPrintDetailsAr: String? = null,
    var enableReceiptDetails: Boolean? = false,
    var isItunesMerchant: Boolean? = false,
    var showBarCode: Boolean? = null,
    var itemExpirationDate: String? = null,
    var showSkuBarcode: Boolean? = false,
    var skuBarcode: String? = null,
    var isServiceCredential: Boolean = false,
    var cartItemCount: Int = 0
) {
    fun getUserNameTitle(): String {
        return if (Globals.lang == "en") {
            productUsernameTitleEn
        } else {
            productUsernameTitleAr
        }
    }

    fun getUserSecretTitle(): String {
        return if (Globals.lang == "en") {
            productSecretTitleEn
        } else {
            productSecretTitleAr
        }
    }

    fun getSerialTitle(): String {
        return if (Globals.lang == "en") {
            productSerialTitleEn
        } else {
            productSerialTitleAr
        }
    }

    fun getDetails(): String {
        return if (Globals.lang == "en") {
            receiptPrintDetailsEn ?: receiptPrintDetailsAr ?: ""
        } else {
            receiptPrintDetailsAr ?: receiptPrintDetailsEn ?: ""
        }
    }

    fun getProductCostPrice(): String {
        return if (costPrice == null) {
            "N/A"
        } else {
            "${costPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getRecommendedPrice(): String {
        return if (recommendedPrice == null) {
            "N/A"
        } else {
            "${recommendedPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getName(): String {
        return if (Globals.lang == "en") {
            nameEn ?: ""
        } else {
            nameAr ?: ""
        }
    }

    fun getProductsId(): Int {
        return if (productId == 0) {
            id
        } else {
            productId
        }
    }

    fun getImage(): String {
        return productSmallImagePath ?: ""
    }

    fun getMerchantName(): String {
        return if (Globals.lang == "en") {
            merchantNameEn ?: ""
        } else {
            merchantNameAr ?: ""
        }
    }

    fun getMerchantLogo(): String {
        return merchantLogoPath ?: ""
    }

    fun getMerchant(): String {
        return merchantId ?: ""
    }

    fun isInStock(): Boolean {
        return (availableStockAmount > 0 || productType == Globals.ProductType.Predefined.value || productType == Globals.ProductType.Service.value)
    }

    fun getPrice(): String {
        return price ?: costPrice ?: ""
    }

    fun getFullPrice(): String {
        return fullPrice ?: ""
    }

    fun getVat(): String {
        return vat ?: ""
    }

    fun getPriceAfterVat(): String {
        return priceAfterVat ?: ""
    }

    fun getPriceAfterVatDouble(qty: Int): Double {
        return (priceAfterVat?.toDoubleOrNull() ?: 0.0) * qty
    }

    fun getPriceDouble(): Double {
        return price?.toDoubleOrNull() ?: 0.0
    }

    fun getExpectProfitDouble(): Double {
        return expectedProfit?.toDoubleOrNull() ?: 0.0
    }

    fun getRecommendedRetailPriceDouble(): Double {
        return recommendedRetailPrice?.toDoubleOrNull() ?: 0.0
    }

    fun getVatOnRecommendedRetailPriceDouble(): Double {
        return vatOnRecommendedPrice?.toDoubleOrNull() ?: 0.0
    }

    fun getTotalVat(): Double {
        return totalVat?.toDoubleOrNull() ?: 0.0
    }

    fun getRecommendedRetailPriceAfterVatDouble(qty: Int): Double {
        return (recommendedRetailPriceAfterVat?.toDoubleOrNull() ?: 0.0) * qty
    }

    fun getVatDouble(): Double {
        return vat?.toDoubleOrNull() ?: 0.0
    }

    fun getSubResellerPrice(): Double {
        return subResellerPrice?.toDoubleOrNull() ?: 0.0
    }

    fun getSubResellerPriceAfterVat(): Double {
        return subResellerPriceAfterVat?.toDoubleOrNull() ?: 0.0
    }

    fun getFullSubResellerPrice(): Double {
        return fullSubResellerPrice?.toDoubleOrNull() ?: 0.0
    }

    fun getFullSubResellerPriceAfterVat(qty: Int): Double {
        return (fullSubResellerPriceAfterVat?.toDoubleOrNull() ?: 0.0) * qty
    }
}
