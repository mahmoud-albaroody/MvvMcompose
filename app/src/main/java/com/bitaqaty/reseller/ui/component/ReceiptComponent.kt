package com.bitaqaty.reseller.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Environment
import android.text.Html
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.data.model.UserInfo
import com.bitaqaty.reseller.databinding.ComponentReceiptBinding
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utilities.DateUtils
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.UlTagHandler
import com.bitaqaty.reseller.utilities.Utils
import java.util.*
import com.bitaqaty.reseller.utilities.Globals.DEV_ID
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.isPartnerApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.net.URL


class ReceiptComponent(
    private val ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val paymentType: String? = ""
) : FrameLayout(ctx, attributeSet, defStyleAttr) {
    var binding: ComponentReceiptBinding? = null

    init {
        binding =
            ComponentReceiptBinding.inflate(LayoutInflater.from(context), this, true)

        LayoutInflater.from(ctx).inflate(R.layout.component_receipt, this, true)
    }

//    fun setData(
//        product: Product,
//        productDetails: ProductDetails,
//        index: Int,
//        secondCopy: Boolean = false
//    ) {
//        if (product.getMerchant().isNotEmpty()) {
//            loadMerchantImage(product.getMerchantLogo(), product.getMerchant())
//        }
//        val counter = "${if (secondCopy) 2 else 1}"
//        printCounter2.text = counter
//        printCounter.text = counter
//        /*txtPurchaseDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
//        productName.text = productDetails.getProductName()
//        purchaseDate.text = DateUtils.getTransLogDateOnly(
//            (productDetails.purchaseDateTime ?: productDetails.purchaseDate).replace(", ", " ")
//        )
//        purchaseTime.text = DateUtils.getTransLogTime(
//            (productDetails.purchaseDateTime ?: productDetails.purchaseDate).replace(", ", " ")
//        )
//
////        productDetails.products?.let {
////            if (it.first().isServiceCredential) {
////                usernameCredentialTxt.text = Utils.fixSecretsForAr(it.first().productSerial)
////            }
////        }
//        /*txtProductName.text = product.getName()*/
//
////        if (Utils.isMadaApp() && paymentType != "cash") {
//        if (paymentType == "mada" && isPartnerApp()) {
//            recommendedTxt.isVisible = true
//            vatTxt.isVisible = true
//            layoutRecommendedRetailPriceAfterVATTxt.isVisible = true
//            vat_amount.isVisible = true
//            recommended.isVisible = true
//            taxable_invoice.isVisible = true
//            line9.isVisible = true
//            layoutCodeVAT.isVisible = true
//            layoutCodeVATTxt.isVisible = true
//            code_number.isVisible = true
//            layoutSG.isVisible = true
//            layoutSM.isVisible = true
//            layoutOS.isVisible = true
//            layoutXO.isVisible = true
//            line10.isVisible = true
//            line11.isVisible = true
//            layoutRecommendedRetailPriceAfterVAT.isVisible = true
//
//
//            recommendedTxt.text = product.recommendedRetailPrice + " " + Utils.getUserCurrency()
//            if (product.recommendedRetailPriceAfterVat != null && product.recommendedRetailPrice != null) {
//                vatTxt.text =
//                    (product.recommendedRetailPriceAfterVat!!.toDouble() - product.recommendedRetailPrice!!.toDouble()).fmt() + " " + Utils.getUserCurrency()
//                layoutRecommendedRetailPriceAfterVATTxt.text =
//                    product.recommendedRetailPriceAfterVat + " " + Utils.getUserCurrency()
//            }
//
//            layoutCodeVATTxt.text =
//                productDetails.vatCode
//            layoutSGEn.text =
//                "“SG”" + productDetails.vatPercentage + "VAT Standard Goods"
//            layoutSGAr.text =
//                productDetails.vatPercentage + "ضريبة على كامل القيمة"
//            layoutSMEn.text =
//                "“SM“" + productDetails.vatPercentage + "VAT Standard Margin"
//            layoutSMAr.text =
//                productDetails.vatPercentage + "ضريبة على القيمة الأعلى من القيمة الإسمية"
//        }
//        company_title.text = "شركة الخليج للتوزيع المباشر. ص.ب 7753, الرياض 12234"
//        setupSkuBarCode(productDetails.skuBarcode, productDetails.showSKUBarcode)
//        productDetails.products?.let {
//            setupProductCredentials(it[index], product)
//            setupReceiptDetails(it[index])
//            Utils.getUserData()?.let { user ->
//                user.reseller?.let { userInfo ->
//                    setupBarCode(
//                        it[index].productSecret ?: it[index].productPassword ?: it[index].barcode,
//                        it[index].showBarCode,
//                        userInfo
//                    )
//
//                    if (userInfo.printResellerInfo == true) {
//                        layoutShop.visibility = VISIBLE
//                        resellerCommercialName.text = userInfo.parentResellerFullName
//                        line.visibility = VISIBLE
//                    } else {
//                        layoutShop.visibility = GONE
//                        resellerCommercialName.visibility = GONE
//                        line.visibility = GONE
//                    }
//
//                    if (userInfo.showReceiptSupportNumbers == true) {
//                        whatsapp.visibility = VISIBLE
//                        hotLine.visibility = VISIBLE
//                    } else {
//                        whatsapp.visibility = GONE
//                        hotLine.visibility = GONE
//                    }
//                    if (userInfo.showReceiptUrl == true) {
//                        url.visibility = VISIBLE
//                    } else {
//                        url.visibility = GONE
//                    }
//                    baanNumber.text = userInfo.baanNumber
//                    /*setupResellerInformation(user)*/
//                }
//            }
//        }
//        /*txtPrintDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
//        txtPrintDate.text = DateUtils.getCurrentDate()
//
//        deviceNumber.text = DEV_ID
//    }

    fun setTransLogReceipt(transLog: TransactionLog) {
        binding?.let { component ->
            if (transLog.getMerchant().isNotEmpty()) {
                loadMerchantImage(transLog.getMerchantLogo(), transLog.getMerchant())
            }
            val counter = transLog.getPrintIntCounter() + 1
            /*txtHeader.text = counter
        txtHeader.visibility = VISIBLE*/
            /*lineHeader.visibility = VISIBLE*/
            /*txtPurchaseDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
            component.productName.text = transLog.getProductName()

            component.printCounter2.text = counter.toString()
            component.printCounter.text = counter.toString()
            component.purchaseDate.text = DateUtils.getTransLogDateOnly(transLog.getCheckingDate())
            component.purchaseTime.text = DateUtils.getTransLogTime(transLog.getCheckingDate())
            /*txtProductName.text = transLog.getProductName()*/
            setupCredentialsFromTransactionLog(transLog)
            setupReceiptDetailsFromTransactionLog(transLog)

            if (paymentType == "mada" && isPartnerApp()) {
                component.recommendedTxt.isVisible = true
                component.vatTxt.isVisible = true
                component.layoutRecommendedRetailPriceAfterVATTxt.isVisible = true
                component.vatAmount.isVisible = true
                component.recommended.isVisible = true
                component.taxableInvoice.isVisible = true
                component.line9.isVisible = true
                component.layoutCodeVAT.isVisible = true
                component.layoutCodeVATTxt.isVisible = true
                component.codeNumber.isVisible = true
                component.layoutSG.isVisible = true
                component.layoutSM.isVisible = true
                component.layoutOS.isVisible = true
                component.layoutXO.isVisible = true
                component.line10.isVisible = true
                component.line11.isVisible = true
                component.layoutRecommendedRetailPriceAfterVAT.isVisible = true

                component.recommendedTxt.text =
                    transLog.recommendedRetailPrice?.fmt() + " " + Utils.getUserCurrency()
                if (transLog.recommendedRetailPriceAfterVAT != null && transLog.recommendedRetailPrice != null) {
                    component.vatTxt.text =
                        (transLog.recommendedRetailPriceAfterVAT!!.toDouble() - transLog.recommendedRetailPrice!!.toDouble()).fmt() + " " + Utils.getUserCurrency()
                    component.layoutRecommendedRetailPriceAfterVATTxt.text =
                        transLog.getCheckingRecommendedRetailPriceAfterVAT().fmt()
                            .toString() + " " + Utils.getUserCurrency()
                }

                component.layoutCodeVATTxt.text =
                    transLog.vatCode
                component.layoutSGEn.text =
                    "“SG”" + transLog.vatPercentage + "VAT Standard Goods"
                component.layoutSGAr.text =
                    transLog.vatPercentage + "ضريبة على كامل القيمة"
                component.layoutSMEn.text =
                    "“SM“" + transLog.vatPercentage + "VAT Standard Margin"
                component.layoutSMAr.text =
                    transLog.vatPercentage + "ضريبة على القيمة الأعلى من القيمة الإسمية"
            }
            component.companyTitle.text = "شركة الخليج للتوزيع المباشر. ص.ب 7753, الرياض 12234"
            Utils.getUserData()?.let { user ->
                user.reseller?.let { userInfo ->
                    setupBarCode(transLog.productSecret, transLog.showBarCode, userInfo)
                    setupSkuBarCode(transLog.skuBarcode, transLog.showSkuBarcode)
                    component.baanNumber.text = userInfo.baanNumber
                    if (userInfo.printResellerInfo == true) {
                        component.layoutShop.visibility = VISIBLE
                        if (user.accountType == Globals.Role.SubAccount.value) {
                            component.resellerCommercialName.text = userInfo.parentResellerFullName
                        } else {
                            component.resellerCommercialName.text = userInfo.fullName
                        }
                        component.line.visibility = VISIBLE
                    } else {
                        component.layoutShop.visibility = GONE
                        component.resellerCommercialName.visibility = GONE
                        component.line.visibility = GONE
                    }
                    if (userInfo.showReceiptSupportNumbers == true) {
                        component.whatsapp.visibility = VISIBLE
                        component.hotLine.visibility = VISIBLE
                    } else {
                        component.whatsapp.visibility = GONE
                        component.hotLine.visibility = GONE
                    }
                    if (userInfo.showReceiptUrl == true) {
                        component.url.visibility = VISIBLE
                    } else {
                        component.url.visibility = GONE
                    }
                }
            }
            if (!transLog.itemExpirationDate.isNullOrEmpty()) {
                component.layoutExpiryDate.visibility = VISIBLE
                component.itemExpirationDate.visibility = VISIBLE
                component.itemExpirationDate.text = transLog.itemExpirationDate
            } else {
                component.layoutExpiryDate.visibility = GONE
                component.itemExpirationDate.visibility = GONE
            }


            /*txtPrintDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
            component.txtPrintDate.text = DateUtils.getCurrentDate()
            component.transNumberTxt.text = transLog.getTransactionRefNumber()
            component.deviceNumber.text = DEV_ID
        }
    }

    private fun setupCredentialsFromTransactionLog(transLog: TransactionLog) {
        binding?.let { comBinding ->
            comBinding.serialTitleEn.text = transLog.productSerialTitleEn
            comBinding.serialTitleAr.text = transLog.productSerialTitleAr
            /*val serialTitle = "${transLog.getProductSerialTitle()}:"*/
            /*txtSerialNoTitle.text = serialTitle*/
            comBinding.productSerialTxt.text = transLog.productSerial
            val secretTitle = "${transLog.getProductSecretTitle()}:"
            comBinding.txtSecretTitle.text = transLog.productSerialTitleAr
            comBinding.txtSecretTitleEn.text = transLog.productSerialTitleEn
            if (transLog.productType == Globals.ProductType.Serial.value && transLog.isItunesMerchant == true) {
                comBinding.layoutPasswordBox.visibility = VISIBLE
                comBinding.layoutProductSecret.visibility = GONE
                (AppCompatResources.getDrawable(
                    context, R.drawable.drawable_password_box
                ) as GradientDrawable?)?.let {
                    it.setStroke(6, Color.BLACK)
                    comBinding.layoutPasswordBox.background = it
                }
                comBinding.txtSecretInBox.text =
                    transLog.getProductSecrett().uppercase(Locale.getDefault())
                /*txtSecret.visibility = GONE*/
            } else if (transLog.productType == Globals.ProductType.Credential.value
                && transLog.productUserName?.isNotBlank() == true
            ) {
                comBinding.layoutUserName.visibility = VISIBLE
                comBinding.productUsername.visibility = VISIBLE
                val usernameTitle = "${transLog.getProductUserNameTitle()}:"
                comBinding.usernameTitleAr.text = transLog.productUserNameTitleAr
                comBinding.usernameTitleEn.text = transLog.productUserNameTitleEn
                comBinding.productUsername.text = transLog.productUserName ?: ""
                comBinding.productSecret.text = transLog.getProductSecrett()
                // layoutPasswordBox.visibility = VISIBLE
            } else {
                comBinding.layoutUserName.isVisible = !transLog.productUserName.isNullOrEmpty()
                comBinding.productUsername.isVisible = !transLog.productUserName.isNullOrEmpty()
                comBinding.usernameTitleAr.text = transLog.productUserNameTitleAr
                comBinding.usernameTitleEn.text = transLog.productUserNameTitleEn
                comBinding.productUsername.text = transLog.productUserName ?: ""
                comBinding.productSecret.text = transLog.getProductSecrett()
                //  layoutPasswordBox.visibility = VISIBLE
            }
        }
    }

    private fun setupBarCode(barcode: String?, showBarcode: Boolean?, userInfo: UserInfo) {
        binding?.let { comBinding ->
            if (showBarcode == true && !barcode.isNullOrBlank()) {
                comBinding.layoutBarCode.visibility = VISIBLE
                val widthPixels = Dimens.width_barcode
                val heightPixels = Dimens.height_barcode

                comBinding.imgBarCode.setImageBitmap(
                    Utils.createBarcodeBitmap(
                        barcodeValue = barcode,
                        widthPixels = widthPixels,
                        heightPixels = heightPixels
                    )
                )
            } else {
                comBinding.layoutBarCode.visibility = GONE
            }
        }
    }

    private fun setupSkuBarCode(skuBarcode: String?, showSkuBarcode: Boolean?) {
        binding?.let { comBinding ->
            if (showSkuBarcode == true && !skuBarcode.isNullOrBlank()) {
                comBinding.layoutSkuBarCodeTitles.visibility = VISIBLE
                comBinding.layoutSkuBarcode.visibility = VISIBLE
                val widthPixels = Dimens.width_barcode
                val heightPixels = Dimens.height_barcode

                comBinding.imgSkuBarCode.setImageBitmap(
                    Utils.createBarcodeBitmap(
                        barcodeValue = skuBarcode,
                        widthPixels = widthPixels,
                        heightPixels = heightPixels
                    )
                )
            }
        }
    }

    private fun setupReceiptDetails(data: TransactionLog) {
        binding?.let { comBinding ->
            data.enableReceiptDetails?.let { enabled ->
                if (enabled && data.receiptPrintDetailsEn?.isNotEmpty() == true) {
                    if (data.getDetails().isNotEmpty()) {
                        comBinding.layoutHowToUseEn.visibility = VISIBLE
                    } else {
                        comBinding.layoutHowToUseEn.visibility = GONE
                    }

                    if (data.receiptPrintDetailsAr?.isNotEmpty() == true) {
                        comBinding.layoutHowToUseAr.visibility = VISIBLE
                    } else {
                        comBinding.layoutHowToUseAr.visibility = GONE
                    }
                    comBinding.lineHowToUse.visibility = VISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (data.receiptPrintDetailsEn?.isNotEmpty() == true) {
                            comBinding.howToUseEn.text = Html.fromHtml(
                                data.receiptPrintDetailsEn,
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                                null,
                                UlTagHandler()
                            )
                        }
                        if (data.receiptPrintDetailsAr?.isNotEmpty() == true) {
                            comBinding.howToUseAr.text = Html.fromHtml(
                                data.receiptPrintDetailsAr,
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                                null,
                                UlTagHandler()
                            )
                        }
                    } else {
                        if (data.receiptPrintDetailsEn?.isNotEmpty() == true) {
                            comBinding.howToUseEn.text =
                                Html.fromHtml(data.receiptPrintDetailsEn, null, UlTagHandler())
                        }
                        if (data.receiptPrintDetailsAr?.isNotEmpty() == true) {
                            comBinding.howToUseAr.text =
                                Html.fromHtml(data.receiptPrintDetailsAr, null, UlTagHandler())
                        }
                    }
                }
            }
        }
    }

    private fun setupReceiptDetailsFromTransactionLog(transLog: TransactionLog) {
        binding?.let { comBinding ->
            transLog.enableReceiptDetails?.let { enabled ->
                if (enabled && transLog.getDetails().isNotEmpty()) {
                    if (transLog.getDetailsEn().isNotBlank()) {
                        comBinding.layoutHowToUseEn.visibility = VISIBLE
                    } else {
                        comBinding.layoutHowToUseEn.visibility = GONE
                    }

                    if (transLog.getDetailsAr().isNotBlank()) {
                        comBinding.layoutHowToUseAr.visibility = VISIBLE
                    } else {
                        comBinding.layoutHowToUseAr.visibility = GONE
                    }
                    comBinding.lineHowToUse.visibility = VISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (transLog.getDetailsEn().isNotBlank()) {
                            comBinding.howToUseEn.text = Html.fromHtml(
                                transLog.getDetailsEn(),
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                                null,
                                UlTagHandler()
                            )
                        }
                        if (transLog.getDetailsAr().isNotBlank()) {
                            comBinding.howToUseAr.text = Html.fromHtml(
                                transLog.getDetailsAr(),
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                                null,
                                UlTagHandler()
                            )
                        }
                    } else {
                        if (transLog.getDetailsEn().isNotBlank()) {
                            comBinding.howToUseEn.text =
                                Html.fromHtml(transLog.getDetailsEn(), null, UlTagHandler())
                        }
                        if (transLog.getDetailsAr().isNotBlank()) {
                            comBinding.howToUseAr.text =
                                Html.fromHtml(transLog.getDetailsAr(), null, UlTagHandler())
                        }
                    }
                }
            }
        }
    }


    private fun loadMerchantImage(imageLog: String, imageName: String) {
        binding?.let { comBinding ->
            val imgFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                imageName
            )
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                comBinding.imgMerchant.setImageBitmap(myBitmap)
            }
//        convertUrlToBitmap(imageLog) {
//            imgMerchant.setImageBitmap(it)
//        }
        }
    }

    private fun convertUrlToBitmap(path: String, bitmap: (Bitmap?) -> Unit) {
        try {
            val url = URL(path)
//            GlobalScope.launch(Dispatchers.IO) {
//                    bitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
//            }
            GlobalScope.launch(Dispatchers.Main) {
                val asyncValue = async(Dispatchers.IO) {
                    try {
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    } catch (e: Exception) {
                        null
                    }
                }
                val value = asyncValue.await()
                bitmap(value)
            }
        } catch (e: IOException) {
            Log.e("error", e.toString())
        }
    }
}