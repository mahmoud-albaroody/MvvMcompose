package com.bitaqaty.reseller.ui.component

import android.app.Activity
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
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductDetails
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.data.model.UserInfo
import com.bitaqaty.reseller.databinding.ComponentReceipt1Binding
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utilities.DateUtils
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.UlTagHandler
import com.bitaqaty.reseller.utilities.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*


class ReceiptComponent1(
    private val ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val paymentType: String? = ""
) : FrameLayout(ctx, attributeSet, defStyleAttr) {
    var binding: ComponentReceipt1Binding? = null

    init {
        //    LayoutInflater.from(ctx).inflate(R.layout.component_receipt1, this, true)
        binding =
            ComponentReceipt1Binding.inflate(LayoutInflater.from(context), this, true)

    }

    fun setData(
        product: Product,
        productDetails: ProductDetails,
        index: Int,
        secondCopy: Boolean = false
    ) {
        binding?.let { comBinding ->
            if (product.getMerchant().isNotEmpty()) {
                loadMerchantImage(product.getMerchantLogo(), product.getMerchant())
            }
            val counter = "${if (secondCopy) 2 else 1}"
            comBinding.printCounter2.text = counter
            comBinding.printCounter.text = counter
            /*txtPurchaseDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/

            comBinding.productName.text = productDetails.getProductName()

            comBinding.purchaseDate.text = DateUtils.getTransLogDateOnly(
                (productDetails.purchaseDateTime ?: productDetails.purchaseDate).replace(", ", " ")
            )
            comBinding.purchaseTime.text = DateUtils.getTransLogTime(
                (productDetails.purchaseDateTime ?: productDetails.purchaseDate).replace(", ", " ")
            )

            if (Utils.isPartnerApp()) {

                comBinding.line9.isVisible = true
            }

            setupSkuBarCode(productDetails.skuBarcode, productDetails.showSKUBarcode)
            productDetails.products?.let {
                setupProductCredentials(it[index], product)
                setupReceiptDetails(it[index])
                Utils.getUserData()?.let { user ->
                    user.reseller?.let { userInfo ->

                        setupBarCode(
                            it[index].productSecret ?: it[index].productPassword
                            ?: it[index].barcode,
                            it[index].showBarCode,
                            userInfo
                        )

                        if (userInfo.printResellerInfo == true) {
                            comBinding.layoutShop.visibility = VISIBLE
                            comBinding.resellerCommercialName.text = userInfo.parentResellerFullName
                            comBinding.line.visibility = VISIBLE
                        } else {
                            comBinding.layoutShop.visibility = GONE
                            comBinding.resellerCommercialName.visibility = GONE
                            comBinding.line.visibility = GONE
                        }
                    }
                }
            }
            /*txtPrintDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
            comBinding.txtPrintDate.text = DateUtils.getCurrentDate()
        }
    }

    fun setTransLogReceipt(transLog: TransactionLog) {
        binding?.let { comBinding ->
            if (transLog.getMerchant().isNotEmpty()) {
                loadMerchantImage(transLog.getMerchantLogo(), transLog.getMerchant())
            }
            val counter = transLog.getPrintIntCounter() + 1
            /*txtHeader.text = counter
        txtHeader.visibility = VISIBLE*/
            /*lineHeader.visibility = VISIBLE*/
            /*txtPurchaseDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
            comBinding.productName.text = transLog.getProductName()

            comBinding.printCounter2.text = counter.toString()
            comBinding.printCounter.text = counter.toString()
            comBinding.purchaseDate.text = DateUtils.getTransLogDateOnly(transLog.date ?: "")
            comBinding.purchaseTime.text = DateUtils.getTransLogTime(transLog.date ?: "")
            /*txtProductName.text = transLog.getProductName()*/
            setupCredentialsFromTransactionLog(transLog)
            setupReceiptDetailsFromTransactionLog(transLog)

            if (Utils.isPartnerApp()) {
                comBinding.line9.isVisible = true


            }

            Utils.getUserData()?.let { user ->
                user.reseller?.let { userInfo ->
                    setupBarCode(transLog.getProductSecrett(), transLog.showBarCode, userInfo)
                    setupSkuBarCode(transLog.skuBarcode, transLog.showSkuBarcode)
                    if (userInfo.printResellerInfo == true) {
                        comBinding.layoutShop.visibility = VISIBLE
                        if (user.accountType == Globals.Role.SubAccount.value) {
                            comBinding.resellerCommercialName.text = userInfo.parentResellerFullName
                        } else {
                            comBinding.resellerCommercialName.text = userInfo.fullName
                        }
                        comBinding.line.visibility = VISIBLE
                    } else {
                        comBinding.layoutShop.visibility = GONE
                        comBinding.resellerCommercialName.visibility = GONE
                        comBinding.line.visibility = GONE
                    }

                }
            }
            if (!transLog.itemExpirationDate.isNullOrEmpty()) {
                comBinding.layoutExpiryDate.visibility = VISIBLE
                comBinding.itemExpirationDate.visibility = VISIBLE
                comBinding.itemExpirationDate.text = transLog.itemExpirationDate
            } else {
                comBinding.layoutExpiryDate.visibility = GONE
                comBinding.itemExpirationDate.visibility = GONE
            }


            /*txtPrintDate.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
            comBinding.txtPrintDate.text = DateUtils.getCurrentDate()

        }
    }

    private fun setupProductCredentials(data: Product, product: Product) {
        binding?.let { comBinding ->


            if (!data.itemExpirationDate.isNullOrEmpty()) {
                comBinding.layoutExpiryDate.visibility = VISIBLE
                comBinding.itemExpirationDate.visibility = VISIBLE
                comBinding.itemExpirationDate.text = data.itemExpirationDate
            } else {
                comBinding.layoutExpiryDate.visibility = GONE
                comBinding.itemExpirationDate.visibility = GONE
            }
            comBinding.serialTitleEn.text = data.productSerialTitleEn
            comBinding.serialTitleAr.text = data.productSerialTitleAr
            /*val serialTitle = "${data.getSerialTitle()}:"*/
            /*txtSerialNoTitle.text = serialTitle*/
            comBinding.productSerialTxt.text = data.productSerial
            val secretTitle = "${data.getUserSecretTitle()}:"
            comBinding.txtSecretTitle.text = data.productSecretTitleAr
            comBinding.txtSecretTitleEn.text = data.productSecretTitleEn


            if (product.productType
                == Globals.ProductType.Serial.value && data.isItunesMerchant == true
            ) {
                comBinding.layoutPasswordBox.visibility = VISIBLE
                comBinding.layoutProductSecret.visibility = GONE
                (AppCompatResources.getDrawable(
                    context, R.drawable.drawable_password_box
                ) as GradientDrawable?)?.let {
                    it.setStroke(6, Color.BLACK)
                    comBinding.layoutPasswordBox.background = it
                }
                comBinding.txtSecretInBox.text =
                    (data.productSecret ?: data.productPassword
                    ?: "").uppercase(Locale.getDefault())
                comBinding.productSecret.visibility = GONE
            } else if (product.productType ==
                Globals.ProductType.Credential.value && !data.productUsername.isNullOrEmpty()
            ) {
                comBinding.layoutUserName.visibility = VISIBLE
                comBinding.productUsername.visibility = VISIBLE
                comBinding.usernameTitleAr.text = data.productUsernameTitleAr
                comBinding.usernameTitleEn.text = data.productUsernameTitleEn
                comBinding.productUsername.text = data.productUsername
                comBinding.productSecret.text = data.productPassword ?: data.productSecret ?: ""
            } else {
                comBinding.productSecret.text = data.productSecret ?: data.productPassword ?: ""
            }

            if (data.isServiceCredential) {
                comBinding.productSerialTxt.text = data.productSerial
                comBinding.usernameTitleAr.text = data.productUsernameTitleAr
                comBinding.usernameTitleEn.text = data.productUsernameTitleEn
                comBinding.productUsername.text = data.productUsername ?: data.transRefNumber ?: ""
                comBinding.layoutUserName.visibility = VISIBLE
                comBinding.productUsername.visibility = VISIBLE
                // layoutPasswordBox.visibility = VISIBLE
                comBinding.productSecret.text = data.productSecret ?: data.productPassword ?: ""
            }
            /*txtSecret.gravity = if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL*/
        }
    }

    private fun setupCredentialsFromTransactionLog(transLog: TransactionLog) {
        binding?.let { comBinding ->
            comBinding.serialTitleEn.text = transLog.productSerialTitleEn
            comBinding.serialTitleAr.text = transLog.productSerialTitleAr
            /*val serialTitle = "${transLog.getProductSerialTitle()}:"*/
            /*txtSerialNoTitle.text = serialTitle*/
            comBinding.productSerialTxt.text = transLog.getProductSeriall()
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

    private fun setupReceiptDetails(data: Product) {
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