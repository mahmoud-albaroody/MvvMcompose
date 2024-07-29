package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import java.io.Serializable

data class Product(
    val productId: Int = 0,
    val costPrice: String? = null,
    val costPriceAfterVat: String? = null,
    private val recommendedPrice: String? = null,
    val recommendedPriceAfterVat: String? = null,
    val nameAr: String? = null,
    val nameEn: String? = null,
    val descriptionAr: String? = null,
    val descriptionEn: String? = null,
    val availableStockAmount: Int = 0,
    private val price: String? = null,
    val fullPrice: String? = null,
    val vat: String? = null,
    val fullVat: String? = null,
    val priceAfterVat: String? = null,
    val fullPriceAfterVat: String? = null,
    val vatPercentage: String? = null,
    val merchantNameAr: String? = null,
    val merchantNameEn: String? = null,
    val productSmallImagePath: String? = null,
    val bulkMin: Int = 0,
    val bulkMax: Int = 0,
    val productType: String? = null,
    val bulkPurchaseEnabled: Boolean = false,
    val bulkServicePurchaseEnabled: Boolean = false,
    val recommendedRetailPrice: String? = null,
    val vatOnRecommendedPrice: String? = null,
    val totalVat: String? = null,
    val recommendedRetailPriceAfterVat: String? = null,
    val subResellerPrice: String? = null,
    val subResellerPriceAfterVat: String? = null,
    val fullSubResellerPrice: String? = null,
    val fullSubResellerPriceAfterVat: String? = null,
    val skuCode: String? = null,
    val vatCode: String? = null,
    val merchantId: String? = null,
    val actualProfit: String? = null,
    val expectedProfit: String? = null,
    val haveValue: Boolean = true,
    val merchantLogoPath: String? = null,
    val isEditable: Boolean = false,
    val isHighlighting: Boolean = false,
    val isOpen: Boolean = false,
    val endUserPriceEnabled: Boolean = false,
    val isAddedToCart: Boolean = false,
    val quantity: Int = 1,
    val id: Int = 0,
    val productSerial: String = "",
    val productUsername: String? = null,
    val productPassword: String? = null,
    val productSecret: String? = null,
    val transRefNumber: String? = null,
    val productUsernameTitleEn: String = "",
    val productSerialTitleEn: String = "",
    val productSecretTitleEn: String = "",
    val productUsernameTitleAr: String = "",
    val productSerialTitleAr: String = "",
    val productSecretTitleAr: String = "",
    val barcode: String? = null,
    val receiptPrintDetailsEn: String? = null,
    val receiptPrintDetailsAr: String? = null,
    val enableReceiptDetails: Boolean? = false,
    val isItunesMerchant: Boolean? = false,
    val showBarCode: Boolean? = null,
    val itemExpirationDate: String? = null,
    val showSkuBarcode: Boolean? = false,
    val skuBarcode: String? = null,
    val isServiceCredential: Boolean = false,
    val cartItemCount: Int = 0
) : Serializable {
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
            "${costPrice.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getRecommendedPrice(): String {
        return if (recommendedPrice == null) {
            "N/A"
        } else {
            "${recommendedPrice.fmt()} ${Utils.getUserCurrency()}"
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

    fun getMerchantName(): String {
        return if (Globals.lang == "en") {
            merchantNameEn ?: ""
        } else {
            merchantNameAr ?: ""
        }
    }

    fun isInStock(): Boolean {
        return (availableStockAmount > 0 || productType == Globals.ProductType.Predefined.value || productType == Globals.ProductType.Service.value)
    }

    fun getPrice(): String {
        return if (price != null) {
            price ?: ""
        } else {
            costPrice ?: ""
        }
    }

    fun getPriceAfterVatDouble(qty: Int): Double {
        return (priceAfterVat?.toDoubleOrNull() ?: 0.0) * qty
    }

    fun getPriceDouble(): Double {
        if (!price.isNullOrEmpty()) {
            return price.toDouble()
        }
        return 0.0
    }

    fun getExpectProfitDouble(): Double {
        if (!expectedProfit.isNullOrEmpty()) {
            return expectedProfit.toDouble()
        }
        return 0.0
    }

    fun getRecommendedRetailPriceDouble(): Double {
        if (!recommendedRetailPrice.isNullOrEmpty()) {
            return recommendedRetailPrice.toDouble()
        }
        return 0.0
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
        if (!vat.isNullOrEmpty()) {
            return vat.toDouble()
        }
        return 0.0
    }

    fun getSubResellerPrice(): Double {
        if (!subResellerPrice.isNullOrEmpty()) {
            return subResellerPrice.toDouble()
        }
        return 0.0
    }

    fun getSubResellerPriceAfterVat(): Double {
        if (!subResellerPriceAfterVat.isNullOrEmpty()) {
            return subResellerPriceAfterVat.toDouble()
        }
        return 0.0
    }

    fun getFullSubResellerPrice(): Double {
        if (!fullSubResellerPrice.isNullOrEmpty()) {
            return fullSubResellerPrice.toDouble()
        }
        return 0.0
    }

    fun getFullSubResellerPriceAfterVat(qty: Int): Double {
        return (fullSubResellerPriceAfterVat?.toDoubleOrNull() ?: 0.0) * qty
    }
}