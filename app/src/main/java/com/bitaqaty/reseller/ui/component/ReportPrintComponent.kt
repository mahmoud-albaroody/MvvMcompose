package com.bitaqaty.reseller.ui.component

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Report
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.data.model.ReportRequestBody
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.databinding.ComponentReceipt1Binding
import com.bitaqaty.reseller.databinding.DailyReportComponentBinding

import com.bitaqaty.reseller.utilities.DateUtils
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.bitaqaty.reseller.utilities.Utils.isResellerAccount
import com.bitaqaty.reseller.utilities.Utils.showProfit
import java.text.SimpleDateFormat
import java.util.*


class ReportPrintComponent constructor(
    private val ctx: Context, private val viewRecommended: Boolean,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(ctx, attributeSet, defStyleAttr) {
    private var showDifferentPrice = false
    private var showReportsForAllAccounts = false
    var isSubAccount = false
    var isBalanceAccount = false
    var showCost = false
    var binding: DailyReportComponentBinding? = null

    init {
//        LayoutInflater.from(ctx).inflate(R.layout.daily_report_component, this, true)
        binding =
            DailyReportComponentBinding.inflate(LayoutInflater.from(context), this, true)

        showReportsForAllAccounts = Utils.showReportsForAllAccounts()
        showDifferentPrice = Utils.showDifferentPrice()
        isBalanceAccount = Utils.getUserData()?.reseller?.resellerType == "BALANCE"
        isSubAccount = Utils.getUserData()?.accountType == "SUB_ACCOUNT"
        showCost = Utils.showCost()
    }

    val SQL_DATE_FORMAT = "yyyy-MM-dd"

    fun setDailyReportData(log: ReportLog, request: ReportRequestBody) {
        binding?.let { comBinding ->
            Utils.getUserData()?.let { user -> setupResellerInformation(user) }
            val fomatter = SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.ENGLISH)
            if (request.searchPeriod.equals(null)) {
                comBinding.txtFromDate.text =
                    fomatter.format(DateUtils.getCustomDate(request.customDateFrom))
                comBinding.txtToDate.text =
                    fomatter.format(DateUtils.getCustomDate(request.customDateTo))
            } else if (request.searchPeriod.equals("TODAY")) {
                comBinding.txtFromDate.text = fomatter.format(getStartOfDay(Date()))
                comBinding.txtToDate.text = fomatter.format(Date())
            } else if (request.searchPeriod.equals("YESTERDAY")) {
                comBinding.txtFromDate.text = fomatter.format(getStartOfDay(getYesterdayDate()))
                comBinding.txtToDate.text = fomatter.format(getEndOfDay(getYesterdayDate()))
            } else if (request.searchPeriod.equals("CURRENT_MONTH")) {
                comBinding.txtFromDate.text = fomatter.format(getCurrentMonthFirstDay())
                comBinding.txtToDate.text = fomatter.format(Date())
            } else if (request.searchPeriod.equals("LAST_MONTH")) {
                comBinding.txtFromDate.text = fomatter.format(getPreviousMonthFirstDay())
                println(comBinding.txtFromDate.text)
                comBinding.txtToDate.text = fomatter.format(getPreviousMonthLastDay())
                println(comBinding.txtToDate.text)
            } else if (request.searchPeriod.equals("LAST_SEVEN_DAYS")) {
                comBinding.txtFromDate.text = fomatter.format(getPreviousSevenDaysBeginning())
                comBinding.txtToDate.text = fomatter.format(getPreviousSevenDaysEnding())
            }

            comBinding.txtFromDate.gravity =
                if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL
            comBinding.txtToDate.gravity =
                if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL
            comBinding.txtNoOfTrans.text = log.numberOfTransactions.toString()
            comBinding.txtCostPrice.text =
                log.transactionsTotalAmount.toString() + " " + Utils.getUserData()?.reseller?.getCurrentCurrency()
                    .toString()

            if (viewRecommended) {
                comBinding.totalRecommendedPrice.isVisible = true
                if (Utils.isResellerAccount()) {
                    comBinding.totalProfit.isVisible = true
                } else {
                    comBinding.totalProfit.isVisible = showProfit()
                }
                comBinding.txtTotalProfitPriceTitle.text =
                    context.getString(R.string.total_profit) + " :"
                comBinding.txtTotalProfitPrice.text = log.getTotalExpectedProfit()
                comBinding.txtTotalRecommendedPrice.text = log.getTotalRecommendedPrice()
            } else {
                comBinding.totalProfit.isVisible = false
                comBinding.totalRecommendedPrice.isVisible = false
            }
            comBinding.totalCostPrice.isVisible = showCost

            for (element in log.elements.orEmpty()) {
                addSubResellerLayout(element)
            }

            Log.i(
                "MyApp",
                "#################################################" + comBinding.txtFromDate.text
            )
            Log.i(
                "MyApp",
                "#################################################" + comBinding.txtToDate.text
            )
        }

    }

    private fun addSubResellerLayout(report: Report) {
        binding?.let { comBinding ->


            val subResellerLayout = comBinding.subResellerLayout

            /**** username title ****/
            val usernameTitleLayout = LinearLayout(context)
            var usernameTitleParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            usernameTitleLayout.layoutParams = usernameTitleParam
            usernameTitleLayout.orientation = LinearLayout.HORIZONTAL
            val subAccountUsernameTitle = TextView(context)
            subAccountUsernameTitle.text = context.getString(R.string.sub_account)
            TextViewCompat.setTextAppearance(subAccountUsernameTitle, R.style.bold_small_font)
            subAccountUsernameTitle.typeface = Typeface.DEFAULT_BOLD
            subAccountUsernameTitle.setTextColor(
                Color.BLACK
            )
            usernameTitleLayout.addView(subAccountUsernameTitle)
            subResellerLayout.addView(usernameTitleLayout)

            /**** username text ****/
            val usernameLayout = LinearLayout(context)
            val usernameParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            usernameLayout.layoutParams = usernameParam
            usernameLayout.orientation = LinearLayout.HORIZONTAL
            val subAccountUsernameText = TextView(context)
            subAccountUsernameText.text = report.subAccountName

            TextViewCompat.setTextAppearance(subAccountUsernameText, R.style.bold_small_font)
            subAccountUsernameText.typeface = Typeface.DEFAULT_BOLD
            subAccountUsernameText.setTextColor(
                Color.BLACK
            )

            usernameLayout.addView(subAccountUsernameText)
            subResellerLayout.addView(usernameLayout)


            /**** product name ****/
            val productLayout = LinearLayout(context);
            var productParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            productLayout.layoutParams = productParam
            productLayout.orientation = LinearLayout.HORIZONTAL
            val productNameTitle = TextView(context)
            productNameTitle.text = context.getString(R.string.product_name)
            val productNAmeText = TextView(context)
            if (lang == "ar") {
                productNAmeText.text = report.productNameAr
            } else {
                productNAmeText.text = report.productNameEn
            }

            TextViewCompat.setTextAppearance(productNameTitle, R.style.bold_small_font)
            TextViewCompat.setTextAppearance(productNAmeText, R.style.bold_small_font)
            productNameTitle.typeface = Typeface.DEFAULT_BOLD
            productNAmeText.typeface = Typeface.DEFAULT_BOLD
            productNameTitle.setTextColor(
                Color.BLACK
            )
            productNAmeText.setTextColor(Color.BLACK)
            productLayout.addView(productNameTitle)
            productLayout.addView(productNAmeText)
            subResellerLayout.addView(productLayout)


            /**** no. of trans ****/
            val noOfTransLayout = LinearLayout(context);
            var noOfTransParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            noOfTransLayout.layoutParams = noOfTransParam
            noOfTransLayout.orientation = LinearLayout.HORIZONTAL
            val noOfTransTitle = TextView(context)
            noOfTransTitle.text = context.getString(R.string.no_of_trans)
            val noOfTransText = TextView(context)
            noOfTransText.text = report.numberOfTrans.toString()

            TextViewCompat.setTextAppearance(noOfTransTitle, R.style.bold_small_font)
            TextViewCompat.setTextAppearance(noOfTransText, R.style.bold_small_font)
            noOfTransTitle.typeface = Typeface.DEFAULT_BOLD
            noOfTransText.typeface = Typeface.DEFAULT_BOLD
            noOfTransTitle.setTextColor(Color.BLACK)
            noOfTransText.setTextColor(Color.BLACK)
            noOfTransLayout.addView(noOfTransTitle)
            noOfTransLayout.addView(noOfTransText)
            subResellerLayout.addView(noOfTransLayout)

            if (Utils.showCost()) {
                /**** cost price ****/
                val costPriceLayout = LinearLayout(context);
                var costPriceParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                costPriceLayout.layoutParams = costPriceParam
                costPriceLayout.orientation = LinearLayout.HORIZONTAL
                val costPriceTitle = TextView(context)
                costPriceTitle.text = context.getString(R.string.cost_price)
                val costPriceText = TextView(context)
                costPriceText.text =
                    report.totalTransAmount()

                TextViewCompat.setTextAppearance(costPriceTitle, R.style.bold_small_font)
                TextViewCompat.setTextAppearance(costPriceText, R.style.bold_small_font)
                costPriceTitle.typeface = Typeface.DEFAULT_BOLD
                costPriceText.typeface = Typeface.DEFAULT_BOLD
                costPriceTitle.setTextColor(
                    Color.BLACK
                )
                costPriceText.setTextColor(
                    Color.BLACK
                )
                costPriceLayout.addView(costPriceTitle)
                costPriceLayout.addView(costPriceText)
                subResellerLayout.addView(costPriceLayout)

            }

            if (viewRecommended) {
                /**** recommended price ****/
                val recommendedPriceLayout = LinearLayout(context)
                val recommendedPriceParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    384,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                recommendedPriceLayout.layoutParams = recommendedPriceParam
                recommendedPriceLayout.orientation = LinearLayout.HORIZONTAL
                val recommendedPriceTitle = TextView(context)

                recommendedPriceTitle.layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    2.0f
                )
                recommendedPriceTitle.text =
                    context.getString(R.string.total_recommended_retail_price)
                val recommendedPriceText = TextView(context)
                recommendedPriceText.layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                    3.0f
                )
                recommendedPriceText.text =
                    report.getTotalRecommendedPrice()

                TextViewCompat.setTextAppearance(recommendedPriceTitle, R.style.bold_small_font)
                TextViewCompat.setTextAppearance(recommendedPriceText, R.style.bold_small_font)
                recommendedPriceTitle.typeface = Typeface.DEFAULT_BOLD
                recommendedPriceText.typeface = Typeface.DEFAULT_BOLD
                recommendedPriceTitle.setTextColor(
                  Color.BLACK
                )
                recommendedPriceText.setTextColor(
                  Color.BLACK
                )
                recommendedPriceLayout.addView(recommendedPriceTitle)
                recommendedPriceLayout.addView(recommendedPriceText)
                subResellerLayout.addView(recommendedPriceLayout)


                /**** total Profit ****/
                val totalProfitLayout = LinearLayout(context)
                val totalProfitParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                totalProfitLayout.layoutParams = totalProfitParam
                totalProfitLayout.orientation = LinearLayout.HORIZONTAL
                val totalProfitTitle = TextView(context)
                totalProfitTitle.text = context.getString(R.string.total_profit) + " :"
                val totalProfitText = TextView(context)
                totalProfitText.text = report.getTotalExpectedProfit()
                TextViewCompat.setTextAppearance(totalProfitTitle, R.style.bold_small_font)
                TextViewCompat.setTextAppearance(totalProfitText, R.style.bold_small_font)
                totalProfitTitle.typeface = Typeface.DEFAULT_BOLD
                totalProfitText.typeface = Typeface.DEFAULT_BOLD
                totalProfitTitle.setTextColor(
                  Color.BLACK
                )
                totalProfitText.setTextColor(
                    Color.BLACK
                )
                if (isResellerAccount()) {
                    totalProfitLayout.addView(totalProfitTitle)
                    totalProfitLayout.addView(totalProfitText)
                    subResellerLayout.addView(totalProfitLayout)
                } else {
                    if (showProfit()) {
                        totalProfitLayout.addView(totalProfitTitle)
                        totalProfitLayout.addView(totalProfitText)
                        subResellerLayout.addView(totalProfitLayout)
                    }
                }

            }

            val breakLine = TextView(context)

            val param: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1)
            param.topMargin = 8
            param.bottomMargin = 8
            breakLine.layoutParams = param
            breakLine.setBackgroundColor(Color.parseColor("black"))
            subResellerLayout.addView(breakLine)
        }
    }

    private fun getYesterdayDate(): Date? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val format = SimpleDateFormat("yyyy-MM-dd")
        format.format(calendar.time)
        return format.calendar.time
    }


    private fun getStartOfDay(date: Date?): Date? {
        if (null == date) {
            return null
        }
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DATE]
        calendar[year, month, day, 0, 0] = 0
        return calendar.time
    }

    private fun getEndOfDay(date: Date?): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DATE]
        calendar[year, month, day, 23, 59] = 59
        return calendar.time
    }

    private fun getPreviousMonthFirstDay(): Date? {
        val aCalendar = Calendar.getInstance()
        aCalendar.add(Calendar.MONTH, -1)
        aCalendar[Calendar.DATE] = aCalendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        aCalendar[Calendar.HOUR] = aCalendar.getActualMinimum(Calendar.HOUR)
        aCalendar.set(Calendar.AM_PM, Calendar.AM)
        aCalendar[Calendar.MINUTE] = aCalendar.getActualMinimum(Calendar.MINUTE)
        aCalendar[Calendar.SECOND] = aCalendar.getActualMinimum(Calendar.SECOND)
        return aCalendar.time
    }

    private fun getPreviousMonthLastDay(): Date? {
        val aCalendar = Calendar.getInstance()
        aCalendar.add(Calendar.MONTH, -1)
        aCalendar[Calendar.DATE] = aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        aCalendar[Calendar.HOUR] = aCalendar.getActualMaximum(Calendar.HOUR)
        aCalendar[Calendar.MINUTE] = aCalendar.getActualMaximum(Calendar.MINUTE)
        aCalendar[Calendar.SECOND] = aCalendar.getActualMaximum(Calendar.SECOND)
        return aCalendar.time
    }

    private fun getPreviousSevenDaysBeginning(): Date? {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -7)
        cal[Calendar.HOUR] = cal.getActualMinimum(Calendar.HOUR)
        cal.set(Calendar.AM_PM, Calendar.AM)
        cal[Calendar.MINUTE] = cal.getActualMinimum(Calendar.MINUTE)
        cal[Calendar.SECOND] = cal.getActualMinimum(Calendar.SECOND)
        return cal.time
    }

    private fun getPreviousSevenDaysEnding(): Date? {
        val formatter = SimpleDateFormat(SQL_DATE_FORMAT)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        cal[Calendar.HOUR] = cal.getActualMaximum(Calendar.HOUR)
        cal[Calendar.MINUTE] = cal.getActualMaximum(Calendar.MINUTE)
        cal[Calendar.SECOND] = cal.getActualMaximum(Calendar.SECOND)
        return cal.time
    }


    private fun setupResellerInformation(user: User) {
        binding?.let {comBinding->
            user.reseller?.let { userInfo ->
                if (user.accountType == Globals.Role.SubAccount.value) {
                    comBinding.txtResellerName.text =
                        userInfo.parentResellerFullName ?: userInfo.parentResellerEmail ?: ""

                    val viewMasterArr =
                        user.reseller?.permissions?.filter { s -> s.id == Globals.PERMISSIONS_IDS.VIEW_REPORTS.value && s.enabled }
                            ?.get(0)?.childPermssions?.filter { s -> s.id == Globals.CHILD_REPORTS_PERMISSIONS_IDS.VIEW_CURRENT_BALANCE.value }
                            ?.get(0)?.enabled

                    if (viewMasterArr != null && viewMasterArr == true) {
                        comBinding.txtCurrentBalance.text =
                            userInfo.balance.fmt() + " " + Utils.getUserData()?.reseller?.getCurrentCurrency()
                                .toString()
                        comBinding.layoutBalance.visibility = VISIBLE
                    } else {
                        comBinding.layoutBalance.visibility = GONE
                    }
                } else {
                    comBinding.txtResellerName.text = userInfo.fullName
                    comBinding.txtCurrentBalance.text =
                        userInfo.balance.fmt() + " " + Utils.getUserData()?.reseller?.getCurrentCurrency()
                            .toString()
                    comBinding.layoutBalance.visibility = VISIBLE
                }
                comBinding.txtResellerName.gravity =
                    if (lang == "en") Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.RIGHT or Gravity.CENTER_VERTICAL
            }
        }

    }


    private fun getCurrentMonthFirstDay(): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        // set day to minimum
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        return calendar.time
    }

    fun getCurrentMonthLastDay(): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        // set day to maximum
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        return calendar.time
    }


}