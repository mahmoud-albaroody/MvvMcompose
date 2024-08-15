package com.bitaqaty.reseller.utilities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.text.*
import android.text.style.LeadingMarginSpan
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.children
import com.bitaqaty.reseller.BuildConfig
import com.bitaqaty.reseller.MainApplication.Companion.gson
import com.bitaqaty.reseller.MainApplication.Companion.mPrefs

import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.User

import com.bitaqaty.reseller.utilities.Globals.AMEX_DATA
import com.bitaqaty.reseller.utilities.Globals.CREDIT_DATA
import com.bitaqaty.reseller.utilities.Globals.CURRENT_INDEX
import com.bitaqaty.reseller.utilities.Globals.CURRENT_LANG
import com.bitaqaty.reseller.utilities.Globals.DEVICE_ID
import com.bitaqaty.reseller.utilities.Globals.ERROR_LOG
import com.bitaqaty.reseller.utilities.Globals.IS_MADA
import com.bitaqaty.reseller.utilities.Globals.IS_MOBILY
import com.bitaqaty.reseller.utilities.Globals.LANG
import com.bitaqaty.reseller.utilities.Globals.LOGIN_PROCESS_TOKEN
import com.bitaqaty.reseller.utilities.Globals.MADA_DATA
import com.bitaqaty.reseller.utilities.Globals.MADA_VERSION
import com.bitaqaty.reseller.utilities.Globals.MADA_VERSION_API
import com.bitaqaty.reseller.utilities.Globals.M_N_C
import com.bitaqaty.reseller.utilities.Globals.RESELLER_Profit_TYPE
import com.bitaqaty.reseller.utilities.Globals.RESET_ACCESS_TOKEN
import com.bitaqaty.reseller.utilities.Globals.SlideDirection
import com.bitaqaty.reseller.utilities.Globals.SlideType
import com.bitaqaty.reseller.utilities.Globals.TOKEN_EXPIRED
import com.bitaqaty.reseller.utilities.Globals.USER_DATA
import com.bitaqaty.reseller.utilities.Globals.USER_NAME
import com.bitaqaty.reseller.utilities.Globals.lang
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import org.xml.sax.XMLReader
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


object Utils {
    private const val TAG = "Utils"


    @JvmStatic
    fun getLang(): String {
        lang = mPrefs?.getString(CURRENT_LANG, "en") ?: "en"
        return lang
    }

    @JvmStatic
    fun loadLocale(context: Context?) {
        context?.let {
            val myLocale = Locale(getLang())
            Locale.setDefault(myLocale)
            val config = Configuration()
            config.setLocale(myLocale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    @JvmStatic
    fun saveLang(lang:String) {
        CURRENT_INDEX = 0
        mPrefs?.putString(CURRENT_LANG, lang)
        mPrefs?.commit()
    }

    //</editor-fold>

    //<editor-fold desc="UI settings">
    @JvmStatic
    fun setAppearance(context: Context?, txt: TextView, style: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txt.setTextAppearance(style)
        } else {
            context?.let { txt.setTextAppearance(context, style) }
        }
    }

    @JvmStatic
    fun getColor(ctx: Context, color: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ctx.resources.getColor(color, null)
        } else {
            ctx.resources.getColor(color)
        }
    }

    fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
        children.forEach { child -> child.restoreHierarchyState(childViewStates) }
    }
    //</editor-fold>

    //<editor-fold desc="User Data Settings">
    @JvmStatic
    fun getLoginProcessToken(): String? {
        return mPrefs?.getString(LOGIN_PROCESS_TOKEN, null)
    }

    @JvmStatic
    fun getResetAccessDataToken(): String? {
        return mPrefs?.getString(RESET_ACCESS_TOKEN, null)
    }

    @JvmStatic
    fun getResellerProfitType(): String? {
        return mPrefs?.getString(RESELLER_Profit_TYPE, null)
    }

    @JvmStatic
    fun saveLoginProcessToken(token: String?) {
        token?.let {
            val splitToken = it.split("/")
            if (splitToken.isNotEmpty()) {
                mPrefs?.putString(LOGIN_PROCESS_TOKEN, splitToken[0])
                mPrefs?.commit()
            } else {
                mPrefs?.putString(LOGIN_PROCESS_TOKEN, token)
                mPrefs?.commit()
            }
        }
    }

    @JvmStatic
    fun saveResetAccessDataToken(token: String?) {
        token?.let {
            val splitToken = it.split("/")
            if (splitToken.isNotEmpty()) {
                mPrefs?.putString(RESET_ACCESS_TOKEN, splitToken[0])
                mPrefs?.commit()
            } else {
                mPrefs?.putString(RESET_ACCESS_TOKEN, token)
                mPrefs?.commit()
            }
        }
    }

    @JvmStatic
    fun saveResellerProfitType(ResellerProfitType: String?) {
        ResellerProfitType?.let {
            if (ResellerProfitType.isNotEmpty()) {
                mPrefs?.putString(RESELLER_Profit_TYPE, it)
                mPrefs?.commit()
            }
        }
    }


    @JvmStatic
    fun getUserData(): User? {
        val json = mPrefs?.getString(USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }

    fun showCost(): Boolean {
        return getUserData()?.accountType == Globals.Role.Reseller.value ||
                getUserData()?.reseller?.permissions?.firstOrNull { s ->
                    s.id == Globals.PERMISSIONS_IDS.VIEW_PRODUCT_DISCOUNT.value
                }?.enabled == true
    }


    fun showPin(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s ->
            s.id == Globals.PERMISSIONS_IDS.VIEW_TRANSACTION_LOG.value
        }?.childPermssions?.firstOrNull { s -> s.id == 24 }?.enabled == true
                || getUserData()?.accountType == "MASTER_ACCOUNT"
    }

    fun showProfit(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s ->
            s.id == Globals.PERMISSIONS_IDS.RECOMMENDED_RETAIL_PRICE.value
        }?.childPermssions?.firstOrNull { s -> s.id == 30 }?.enabled == true
    }

    fun showCanSendSettlement(): Boolean {
        return getUserData()?.reseller?.permissions?.find { s -> s.id == Globals.PERMISSIONS_IDS.CAN_SEND_SETTLEMENT.value }?.enabled == true
    }

    fun editHomePage(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.EDIT_HOME_PAGE.value }?.enabled == true
    }

    fun reprintReceipt(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s ->
            s.id == Globals.PERMISSIONS_IDS.VIEW_TRANSACTION_LOG.value
        }?.childPermssions?.firstOrNull { s -> s.id == 23 }?.enabled == true
                || getUserData()?.accountType == "MASTER_ACCOUNT"
    }


    fun showDifferentPrice(): Boolean {
        return getUserData()?.accountType == Globals.Role.SubAccount.value &&
                getUserData()?.reseller?.canSetSubResellerPrice ?: false ||
                getUserData()?.accountType == Globals.Role.Reseller.value &&
                getUserData()?.reseller?.canSetSubResellerPrice ?: false

    }


    @JvmStatic
    fun isResellerAccount(): Boolean {
        return getUserData()?.accountType == Globals.Role.Reseller.value
    }

    fun isBalance(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.VIEW_MASTER_BALANCE.value }?.enabled == true
    }

    fun showRecommended(): Boolean {
        return getUserData()?.accountType == Globals.Role.Reseller.value ||
                getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.RECOMMENDED_RETAIL_PRICE.value }?.enabled == true
    }


    fun showAllAccounts(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s ->
            s.id == Globals.PERMISSIONS_IDS.VIEW_TRANSACTION_LOG.value
        }?.childPermssions?.firstOrNull { s -> s.id == 19 }?.enabled == true
    }


    fun showReportsForAllAccounts(): Boolean {
        return getUserData()?.accountType == Globals.Role.Reseller.value ||
                getUserData()?.reseller?.permissions?.firstOrNull { s ->
                    s.id == Globals.PERMISSIONS_IDS.VIEW_REPORTS.value
                }?.childPermssions?.firstOrNull { s -> s.id == 22 }?.enabled == true
    }

    fun getUserCurrency(): String {
        return getUserData()?.reseller?.getCurrentCurrency() ?: ""
    }


    fun canPurchase(): Boolean {
        return getUserData()?.accountType == Globals.Role.SubAccount.value &&
                getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.PURCHASE.value }?.enabled == true
    }

    fun canSubAccessTransactionLog(): Boolean {
        return getUserData()?.accountType == Globals.Role.SubAccount.value
                && getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.VIEW_TRANSACTION_LOG.value }?.enabled == true
    }

    fun canAccessSupport(): Boolean {
        return getUserData()?.reseller?.permissions?.firstOrNull { s -> s.id == Globals.PERMISSIONS_IDS.VIEW_SUPPORT_CENTER.value }?.enabled == true
    }

    @JvmStatic
    fun saveUserData(user: User?) {
        user?.reseller?.username?.let {
            saveUsername(it)
        }
        CurrentUser.saveInstance(user)
        val json = gson.toJson(user)
        mPrefs?.putString(USER_DATA, json)
        mPrefs?.commit()
    }

    @JvmStatic
    fun saveMadaApp(isMada: Boolean) {
        mPrefs?.putBoolean(IS_MADA, isMada)
        mPrefs?.commit()
    }



    @JvmStatic
    fun isMadaApp(): Boolean {
        return BuildConfig.IS_SURE
        //  return  false
    }

    @JvmStatic
    fun isPartnerApp(): Boolean {
        return BuildConfig.IS_PARTNER || getUserData()?.reseller?.partner == true
    }

    @JvmStatic
    fun isShowCart(): Boolean {
        //     return true
        return getUserData()?.reseller?.showCart == true
    }

    @JvmStatic
    fun isCashInApp(): Boolean {
        return BuildConfig.IS_CASH
    }

    @JvmStatic
    fun isNearPayApp(): Boolean {
        return getUserData()?.reseller?.partner ?: false
    }

    @JvmStatic
    fun deleteUserData() {
        CurrentUser.saveInstance(null)
        mPrefs?.putString(USER_DATA, null)
        mPrefs?.commit()
    }

    private fun saveUsername(username: String) {
        mPrefs?.putString(USER_NAME, username)
        mPrefs?.commit()
    }

    fun saveMadaVersion(madaVersion: String) {
        mPrefs?.putString(MADA_VERSION, madaVersion)
        mPrefs?.commit()
    }


    fun getMadaVersion(): String? {
        return mPrefs?.getString(MADA_VERSION, null)
    }


    fun saveMadaVersionApi(madaVersion: String) {
        mPrefs?.putString(MADA_VERSION_API, madaVersion)
        mPrefs?.commit()
    }


    fun getMadaVersionApi(): String {
        return mPrefs?.getString(MADA_VERSION_API, null).toString()
    }


    fun saveMNC(MMS: String) {
        mPrefs?.putString(M_N_C, MMS)
        mPrefs?.commit()
    }


    fun getMNC(): String? {
        return mPrefs?.getString(M_N_C, null)
    }

    fun saveIsMobily(isMobily: Boolean) {
        mPrefs?.putBoolean(IS_MOBILY, isMobily)
        mPrefs?.commit()
    }


    fun isMobily(): Boolean {
        return mPrefs?.getBoolean(IS_MOBILY, false) ?: false
    }

    fun getSavedUsername(): String? {
        return mPrefs?.getString(USER_NAME, null)
    }

    fun deleteSavedUsername() {
        mPrefs?.putString(USER_NAME, null)
        mPrefs?.commit()
    }
    //</editor-fold>

    //<editor-fold desc="Device calculations">
    @JvmStatic
    fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

//    @JvmStatic
//    fun getActionBarHeight(context: Context): Int {
//        var actionBarHeight = 0
//        val tv = TypedValue()
//        if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
//            actionBarHeight =
//                TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
//        }
//        return actionBarHeight
//    }

    @JvmStatic
    fun isRTL(context: Context): Boolean {
        val config = context.resources.configuration
        return config.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    @JvmStatic
    fun isTablet(ctx: Context): Boolean {
        return ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    @JvmStatic
    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    @JvmStatic
    fun getRandomUUID(): UUID {
        return UUID.randomUUID()
        // return "621ac39ac2d44175" // noha.nasrat+654@onecard.net Aa12345678
//        return "0FA46DAD-EF19-4D70-8B2D-8E76B6F4885A" //ios device
        //return "5ba6899af329996" //NTIME SUB
        //return "9DB6F521-F212-4B00-B45A-0B5C4EEE9315"
        //return "4184CE9E-DDCF-40CA-A691-2F64F6FAA0D9" //NOURA IOS SUBACCOUNT
        //return "6581EA8E-B344-436B-94C8-3128A7C32648" //NOURA reseller iOS
        //return  "7ea62e3c0e12652b" // ANDROID RESULLER
        // return "9521585fc65cc58a" // Reseller username : one999card@gmail.com , Password : Aa123456

    }

    @JvmStatic
    fun saveDeviceId(id: String) {
        mPrefs?.putString(DEVICE_ID, id)
    }

    fun getLog(): String {
        return mPrefs?.getString(ERROR_LOG, "") ?: ""
    }

    @JvmStatic
    fun saveLog(log: String?) {
        var currentLog = mPrefs?.getString(ERROR_LOG, "") ?: ""
        currentLog = "$currentLog\n$log"
        mPrefs?.putString(ERROR_LOG, currentLog)
    }
    //</editor-fold>

    //<editor-fold desc="UI Actions">
    @JvmStatic
    fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } else {
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    @JvmStatic
    fun showToast(context: Context?, msg: String?) {
        context?.let {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    fun showToast(context: Context?, id: Int) {
        context?.let {
            showToast(context, it.getString(id))
        }
    }
//
//    @JvmStatic
//    fun showSessionEndedDialog(context: Context?, onFinishListener: OnFinishListener? = null) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                Globals.IconType.Warning.value,
//                context.getString(R.string.err_unauthorized),
//                context.getString(R.string.sign_in)
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            dialog.show()
//        }
//    }

//    @JvmStatic
//    fun showAlertDialog(
//        context: Context?,
//        msg: String,
//        type: Globals.IconType,
//        onFinishListener: OnFinishListener? = null
//    ) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                type.value,
//                msg,
//                context.getString(R.string.done)
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            dialog.show()
//        }
//    }
//
//
//    @JvmStatic
//    fun showRetryAlertDialog(
//        context: Context?,
//        msg: String,
//        type: Globals.IconType,
//        onFinishListener: OnFinishListener? = null
//    ) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                type.value,
//                msg,
//                context.getString(R.string.retry)
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            dialog.show()
//        }
//    }

//    @JvmStatic
//    fun showAlertDialog(
//        context: Context?,
//        id: Int,
//        type: Globals.IconType = Globals.IconType.Warning,
//        onFinishListener: OnFinishListener? = null
//    ) {
//        context?.let {
//            showAlertDialog(it, it.getString(id), type, onFinishListener)
//        }
//    }
//
//    @JvmStatic
//    fun showConfirmDialog(
//        context: Context?, id: Int, confirmId: Int,
//        type: Globals.IconType = Globals.IconType.Warning,
//        onFinishListener: OnFinishListener? = null
//    ) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                type.value,
//                context.getString(id),
//                context.getString(confirmId),
//                context.getString(R.string.cancel)
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            dialog.show()
//        }
//    }

//    @JvmStatic
//    fun showConfirmDialog(
//        context: Context?, content: String, confirmId: Int,
//        type: Globals.IconType = Globals.IconType.Warning,
//        onFinishListener: OnFinishListener? = null,
//        onCancelListener: OnFinishListener? = null,
//        isMandatory: Boolean? = null,
//
//        ) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                type.value,
//                content,
//                context.getString(confirmId),
//                context.getString(R.string.cancel),
//                isMandatory
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            if (isMandatory != null && isMandatory == false) {
//                dialog.setOnBtn2Clicker(onCancelListener)
//            }
//            dialog.show()
//        }
//    }

//    @JvmStatic
//    fun showErrorDialog(context: Context?, msg: String) {
//        context?.let {
//            showAlertDialog(it, msg, Globals.IconType.Warning)
//        }
//    }

//    @JvmStatic
//    fun showErrorDialog(context: Context?, id: Int) {
//        context?.let {
//            showAlertDialog(it, id, Globals.IconType.Warning)
//        }
//    }

//    @JvmStatic
//    fun showErrorDialog(context: Context?, msg: String, onFinishListener: OnFinishListener) {
//        context?.let {
//            val dialog = AlertDialog(
//                context,
//                Globals.IconType.Warning.value,
//                msg,
//                context.getString(R.string.ok)
//            )
//            dialog.setOnBtn1Clicker(onFinishListener)
//            dialog.show()
//        }
//    }
//
//    @JvmStatic
//    fun showErrorDialog(context: Context?, id: Int, onFinishListener: OnFinishListener) {
//        context?.let {
//            showErrorDialog(context, context.getString(id), onFinishListener)
//        }
//    }
    //</editor-fold>

    //<editor-fold desc="Validation">
    @JvmStatic
    fun containNumbers(name: String): Boolean {
        val numbers = "1234567890"
        val chars = name.toCharArray()
        for (c in chars) {
            if (numbers.contains("" + c)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun containLetters(name: String): Boolean {
        val smallLetters = "abcdefghijklmnopqrstuvwxyz"
        val chars = name.toCharArray()
        for (c in chars) {
            if (smallLetters.contains("" + c)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun containCapLetters(name: String): Boolean {
        val capLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val chars = name.toCharArray()
        for (c in chars) {
            if (capLetters.contains("" + c)) {
                return true
            }
        }
        return false
    }
    //</editor-fold>

    //<editor-fold desc="Text Operations">
    @JvmStatic
    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    fun String.localized(): String {
        if (lang == "en") {
            return this
        }
        var result = ""
        var en: Char
        for (ch in this) {
            en = ch
            when (ch) {
                '0' -> en = '٠'
                '1' -> en = '١'
                '2' -> en = '٢'
                '3' -> en = '٣'
                '4' -> en = '٤'
                '5' -> en = '٥'
                '6' -> en = '٦'
                '7' -> en = '٧'
                '8' -> en = '٨'
                '9' -> en = '٩'
            }
            result = "${result}$en"
        }
        return result
    }

    fun String.convertToArabic(): String {
        if (lang == "en") {
            return this
        }
        var result = ""
        var en: Char
        for (ch in this) {
            en = ch
            when (ch) {
                '0' -> en = '٠'
                '1' -> en = '١'
                '2' -> en = '٢'
                '3' -> en = '٣'
                '4' -> en = '٤'
                '5' -> en = '٥'
                '6' -> en = '٦'
                '7' -> en = '٧'
                '8' -> en = '٨'
                '9' -> en = '٩'
            }
            result = "${result}$en"
        }
        return result
    }

    fun String.convertToEnglish(): String {
        var result = ""
        var en: Char
        for (ch in this) {
            en = ch
            when (ch) {
                '٠' -> en = '0'
                '١' -> en = '1'
                '٢' -> en = '2'
                '٣' -> en = '3'
                '٤' -> en = '4'
                '٥' -> en = '5'
                '٦' -> en = '6'
                '٧' -> en = '7'
                '٨' -> en = '8'
                '٩' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }

    fun Float.fmt(): String {
        val symbols = DecimalFormatSymbols(Locale.ENGLISH)
        val format = DecimalFormat("#.##", symbols)
        format.isDecimalSeparatorAlwaysShown = false
        return format.format(this)
    }

    fun Double.fmt(): String {
        val symbols = DecimalFormatSymbols(Locale.ENGLISH)
        val format = DecimalFormat("#.##", symbols)
        format.isDecimalSeparatorAlwaysShown = false
        return format.format(this)
    }

    fun Long.fmt(): String {
        return try {
            val d = this.toDouble()
            d.fmt()
        } catch (e: java.lang.Exception) {
            "$this"
        }
    }

    fun Int.fmt(): String {
        return try {
            val d = this.toDouble()
            d.fmt()
        } catch (e: java.lang.Exception) {
            "$this"
        }
    }

    fun String.fmt(): String {
        return try {
            val d = this.toDouble()
            d.fmt()
        } catch (e: java.lang.Exception) {
            this
        }
    }
    //</editor-fold>

    fun View.slideAnimation(direction: SlideDirection, type: SlideType, duration: Long = 500) {
        val fromX: Float
        val toX: Float
        val fromY: Float
        val toY: Float
        val array = IntArray(2)
        getLocationInWindow(array)
        if ((type == SlideType.HIDE && (direction == SlideDirection.RIGHT || direction == SlideDirection.DOWN)) ||
            (type == SlideType.SHOW && (direction == SlideDirection.LEFT || direction == SlideDirection.UP))
        ) {
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val deviceWidth = displayMetrics.widthPixels
            val deviceHeight = displayMetrics.heightPixels
            array[0] = deviceWidth
            array[1] = deviceHeight
        }
        when (direction) {
            SlideDirection.UP -> {
                fromX = 0f
                toX = 0f
                fromY = if (type == SlideType.HIDE) 0f else (array[1] + height).toFloat()
                toY = if (type == SlideType.HIDE) -1f * (array[1] + height) else 0f
            }

            SlideDirection.DOWN -> {
                fromX = 0f
                toX = 0f
                fromY = if (type == SlideType.HIDE) 0f else -1f * (array[1] + height)
                toY = if (type == SlideType.HIDE) 1f * (array[1] + height) else 0f
            }

            SlideDirection.LEFT -> {
                fromX = if (type == SlideType.HIDE) 0f else 1f * (array[0] + width)
                toX = if (type == SlideType.HIDE) -1f * (array[0] + width) else 0f
                fromY = 0f
                toY = 0f
            }

            SlideDirection.RIGHT -> {
                fromX = if (type == SlideType.HIDE) 0f else -1f * (array[0] + width)
                toX = if (type == SlideType.HIDE) 1f * (array[0] + width) else 0f
                fromY = 0f
                toY = 0f
            }
        }
        val animate = TranslateAnimation(
            fromX,
            toX,
            fromY,
            toY
        )
        animate.duration = duration
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if (type == SlideType.HIDE) {
                    visibility = View.INVISIBLE
                }
            }

            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }

        })
        startAnimation(animate)
    }

    fun append(arr: IntArray, element: Int): IntArray {
        val list: MutableList<Int> = arr.toMutableList()
        list.add(element)
        return list.toIntArray()
    }

//    @JvmStatic
//    fun getCardData(type: Int): CardData? {
//        var key = ""
//        when (type) {
//            Globals.RECHARGE_TYPE.MADA.value -> {
//                key = MADA_DATA
//            }
//            Globals.RECHARGE_TYPE.CREDIT.value -> {
//                key = CREDIT_DATA
//            }
//            Globals.RECHARGE_TYPE.AMEX.value -> {
//                key = AMEX_DATA
//            }
//        }
//        var customerId = -1
//        if (getUserData() != null) {
//            val user = getUserData()
//            user?.reseller?.let {
//                customerId = it.id
//            }
//        }
//        key = "${key}_$customerId"
//        val json = mPrefs?.getString(key, "")
//        return gson.fromJson(json, CardData::class.java)
//
//    }
//
//    @JvmStatic
//    fun saveCardData(type: Int, cardData: CardData?) {
//        val json = gson.toJson(cardData)
//        var key = ""
//        when (type) {
//            Globals.RECHARGE_TYPE.MADA.value -> {
//                key = MADA_DATA
//            }
//            Globals.RECHARGE_TYPE.CREDIT.value -> {
//                key = CREDIT_DATA
//            }
//            Globals.RECHARGE_TYPE.AMEX.value -> {
//                key = AMEX_DATA
//            }
//        }
//        var customerId = -1
//        if (getUserData() != null) {
//            val user = getUserData()
//            user?.reseller?.let {
//                customerId = it.id
//            }
//        }
//        key = "${key}_$customerId"
//        mPrefs?.putString(key, json)
//        mPrefs?.commit()
//
//    }

//    @JvmStatic
//    fun deleteCardData(type: Int) {
//        var key = ""
//        when (type) {
//            Globals.RECHARGE_TYPE.MADA.value -> {
//                key = MADA_DATA
//            }
//            Globals.RECHARGE_TYPE.CREDIT.value -> {
//                key = CREDIT_DATA
//            }
//            Globals.RECHARGE_TYPE.AMEX.value -> {
//                key = AMEX_DATA
//            }
//        }
//        var customerId = -1
//        if (getUserData() != null) {
//            val user = getUserData()
//            user?.reseller?.let {
//                customerId = it.id
//            }
//        }
//        key = "${key}_$customerId"
//        mPrefs?.putString(key, null)
//        mPrefs?.commit()
//    }

    fun createBarcodeBitmap(barcodeValue: String, widthPixels: Int, heightPixels: Int): Bitmap {
        val bitMatrix =
            Code128Writer().encode(barcodeValue, BarcodeFormat.CODE_128, widthPixels, heightPixels)

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }

    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null
        val drawingCacheEnabled = view.isDrawingCacheEnabled
        val willNotCacheDrawing = view.willNotCacheDrawing()
        view.isDrawingCacheEnabled = true
        view.setWillNotCacheDrawing(false)
        var drawingCache = view.drawingCache
        val bitmap: Bitmap
        if (null == drawingCache) {
            view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            view.buildDrawingCache()
            drawingCache = view.drawingCache
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache)
            } else {
                bitmap = Bitmap.createBitmap(
                    view.measuredWidth,
                    view.measuredHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                view.draw(canvas)
            }
        } else {
            bitmap = Bitmap.createBitmap(drawingCache)
        }
        view.destroyDrawingCache()
        view.setWillNotCacheDrawing(willNotCacheDrawing)
        view.isDrawingCacheEnabled = drawingCacheEnabled
        return bitmap
    }

    fun createIndentedText(
        text: String,
        marginFirstLine: Int,
        marginNextLines: Int
    ): SpannableString? {
        val result = SpannableString(text)
        result.setSpan(
            LeadingMarginSpan.Standard(marginFirstLine, marginNextLines),
            0,
            text.length,
            0
        )
        return result
    }

    operator fun Spannable.plus(other: Spannable): Spannable {
        return SpannableStringBuilder(this).append(other)
    }

    fun hideKeyboard(activity: Activity?, view: View) {
        val inputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun copyFileToDownloads(context: Context, downloadedFile: File, fileName: String) {
        val DOWNLOAD_DIR =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val resolver = context.contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.MediaColumns.DISPLAY_NAME, fileName + " " +
                            DateUtils.getCurrentDate()
                )
                put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.ms-excel")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)

            }
            resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        } else {
            val authority = "${context.packageName}.provider"
            val destinyFile = File(
                DOWNLOAD_DIR, fileName + " " +
                        DateUtils.getCurrentDate()
            )
            FileProvider.getUriForFile(context, authority, destinyFile)
        }?.also { downloadedUri ->
            try {
                resolver.openOutputStream(downloadedUri).use { outputStream ->
                    val brr = ByteArray(1024)
                    var len: Int
                    val bufferedInputStream =
                        BufferedInputStream(FileInputStream(downloadedFile.absoluteFile))
                    while ((bufferedInputStream.read(brr, 0, brr.size).also { len = it }) != -1) {
                        outputStream?.write(brr, 0, len)
                    }
                    outputStream?.flush()
                    bufferedInputStream.close()
                }
            } catch (e: Exception) {
            }

        }
    }

    fun isWhole(value: Double): Boolean {
        return value - value.toInt() == 0.0
    }

    fun fixSecretsForAr(str: String): CharSequence? {
        if (Globals.lang == "ar") {
            var spaced = false
            if (str.contains(" ")) {
                spaced = true
            } else {
                return str
            }
            val splittedSecret: List<String> = str.split(" ")
            if (splittedSecret.size > 1) {
                var numeric = true
                val joined = splittedSecret.joinToString("")
                try {
                    val num = joined.toLong()
                } catch (e: NumberFormatException) {
                    try {
                        val splittedSecret = str.split(" ", "-")
                        val joined = splittedSecret.joinToString("")
                        val longNum = joined.toLong()
                        numeric = true
                    } catch (e: NumberFormatException) {
                        numeric = false
                    }
                }
                if (numeric) {
                    return splittedSecret.reversed().joinToString(" ")
                } else {
                    return str
                }
            } else {
                return str
            }
        } else {
            return str
        }
    }

}

class UlTagHandler : Html.TagHandler {
    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader?) {
        if (tag == "ul" && !opening) output.append("\n")
        if (tag == "li" && opening) output.append("\n•\t")
    }


}