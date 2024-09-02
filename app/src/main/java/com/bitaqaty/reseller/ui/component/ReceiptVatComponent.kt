package com.bitaqaty.reseller.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductDetails
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.databinding.ComponentVatReceiptBinding
import com.bitaqaty.reseller.utilities.DateUtils
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.isPartnerApp


class ReceiptVatComponent(
    private val ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val paymentType: String? = ""
) : FrameLayout(ctx, attributeSet, defStyleAttr) {
    var binding: ComponentVatReceiptBinding? = null

    init {
        binding =
            ComponentVatReceiptBinding.inflate(LayoutInflater.from(context), this, true)
        //   LayoutInflater.from(ctx).inflate(R.layout.component_receipt1, this, true)
    }

    fun setData(
        product: TransactionLog,
        productDetails: ProductDetails,
        index: Int,
        secondCopy: Boolean = false
    ) {
        binding?.let { component ->
            if (isPartnerApp()) {
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
                component.companyTitle.text = "شركة الخليج للتوزيع المباشر. ص.ب 7753, الرياض 12234"
                component.recommendedTxt.text =
                    product.getCheckingRecommendedRetailPrice() + " " + Utils.getUserCurrency()
                if (product.recommendedRetailPriceAfterVAT != null && product.recommendedRetailPrice != null) {
                    component.vatTxt.text =
                        (product.recommendedRetailPriceAfterVAT!!.toDouble() - product.recommendedRetailPrice!!.toDouble()).fmt() + " " + Utils.getUserCurrency()
                    component.layoutRecommendedRetailPriceAfterVATTxt.text =
                        product.getCheckingRecommendedRetailPriceAfterVAT() + " " + Utils.getUserCurrency()
                }

                component.layoutCodeVATTxt.text =
                    productDetails.vatCode
                component.layoutSGEn.text =
                    "“SG”" + productDetails.vatPercentage + "VAT Standard Goods"
                component.layoutSGAr.text =
                    productDetails.vatPercentage.toString() + "ضريبة على كامل القيمة"
                component.layoutSMEn.text =
                    "“SM“" + productDetails.vatPercentage + "VAT Standard Margin"
                component.layoutSMAr.text =
                    productDetails.vatPercentage.toString() + "ضريبة على القيمة الأعلى من القيمة الإسمية"
            }


            productDetails.products?.let {
                setupProductCredentials(it[index], product)
                Utils.getUserData()?.let { user ->
                    user.reseller?.let { userInfo ->
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
                        component.baanNumber.text = userInfo.baanNumber
                    }
                }
            }
            component.txtPrintDate.text = DateUtils.getCurrentDate()
            component.deviceNumber.text = Globals.DEV_ID
        }
    }

    fun setTransLogReceipt(transLog: TransactionLog) {
        setupCredentialsFromTransactionLog(transLog)
        binding?.let { component ->
            if (isPartnerApp()) {
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
                component.companyTitle.text = "شركة الخليج للتوزيع المباشر. ص.ب 7753, الرياض 12234"
                component.recommendedTxt.text =
                    transLog.recommendedRetailPrice?.fmt() + " " + Utils.getUserCurrency()
                if (transLog.recommendedRetailPriceAfterVAT != null && transLog.recommendedRetailPrice != null) {
                    component.vatTxt.text =
                        (transLog.recommendedRetailPriceAfterVAT!!.toDouble() - transLog.recommendedRetailPrice!!.toDouble()).fmt() + " " + Utils.getUserCurrency()
                    component.layoutRecommendedRetailPriceAfterVATTxt.text =
                        transLog.recommendedRetailPriceAfterVAT!!.fmt() + " " + Utils.getUserCurrency()
                }

                component.layoutCodeVATTxt.text =
                    transLog.vatCode
                component.layoutSGEn.text =
                    "“SG”" + transLog.vatPercentage + "VAT Standard Goods"
                component.layoutSGAr.text =
                    transLog.vatPercentage.toString() + "ضريبة على كامل القيمة"
                component.layoutSMEn.text =
                    "“SM“" + transLog.vatPercentage + "VAT Standard Margin"
                component.layoutSMAr.text =
                    transLog.vatPercentage.toString() + "ضريبة على القيمة الأعلى من القيمة الإسمية"
            }

            Utils.getUserData()?.let { user ->
                user.reseller?.let { userInfo ->
                    component.baanNumber.text = userInfo.baanNumber

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
            component.txtPrintDate.text = DateUtils.getCurrentDate()
            component.transNumberTxt.text = transLog.getTransactionRefNumber()
            component.deviceNumber.text = Globals.DEV_ID
        }
    }

    private fun setupProductCredentials(data: TransactionLog, product: TransactionLog) {

        binding?.transNumberTxt?.text = data.getTransactionRefNumber()
        if (product.productType
            == Globals.ProductType.Serial.value && data.isItunesMerchant == true
        ) {

            (AppCompatResources.getDrawable(
                context, R.drawable.drawable_password_box
            ) as GradientDrawable?)?.let {
                it.setStroke(6, Color.BLACK)
            }

        } else if (product.productType ==
            Globals.ProductType.Credential.value && !data.productUserName.isNullOrEmpty()
        ) {

        }
    }

    private fun setupCredentialsFromTransactionLog(transLog: TransactionLog) {

        if (transLog.productType == Globals.ProductType.Serial.value && transLog.isItunesMerchant == true) {

            (AppCompatResources.getDrawable(
                context, R.drawable.drawable_password_box
            ) as GradientDrawable?)?.setStroke(6, Color.BLACK)
        }
    }
}