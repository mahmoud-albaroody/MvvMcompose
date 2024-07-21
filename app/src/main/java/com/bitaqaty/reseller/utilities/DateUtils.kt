package com.bitaqaty.reseller.utilities

import android.util.Log
import com.bitaqaty.reseller.utilities.Globals.lang
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DateUtils {

    fun getlastVisitDate(strDate: Long): String {
        val df = SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.ENGLISH)
        return df.format(strDate)
    }

    fun getStrDate(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        return df.format(date)
    }

    fun getLocalizeStrDate(date: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return df.format(date)
    }

    fun getDate(strDate: String): Date {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        return df.parse(strDate) ?: Date()
    }

    fun getTransferDate(strDate: String): Date {
        var df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date = df.parse(strDate)
        if (date == null) {
            df = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            date = df.parse(strDate)
        }
        return date ?: Date()
    }

    fun getTransferStrDate(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return df.format(date)
    }

    fun getLocalizedTransferDate(date: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return df.format(date)
    }

    fun getTransDate(date: Date): String {
        val df = SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.ENGLISH)
        return df.format(date)
    }

    fun getCurrentDate(): String {
        val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
        return df.format(Date())
    }

    fun getTransLogDate(value: String): String {
        var df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        try {
            val date = df.parse(value)
            if (date != null) {
                df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
                return df.format(date)
            }
        } catch (e: Exception) {
        }
        return value
    }

    fun getTransLogDateOnly(value: String): String {
        var df = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        try {
            val date = df.parse(value)
            if (date != null) {
                df = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                return df.format(date)
            }
        } catch (e: Exception) {
        }
        return value
    }

    fun getTransLogTime(value: String): String {
        var df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        try {
            val date = df.parse(value)
            if (date != null) {
                df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
                var time = df.format(date).split(" ")[1]
                var a = df.format(date).split(" ")[2]
                return "$time $a"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return value
    }

    fun getStrCustomDate(date: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
        return df.format(date)
    }

    fun getLocalizeStrCustomDate(date: Date): String {
        val df = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return df.format(date)
    }

    fun getCustomDate(strDate: String?): Date {
        var date = Date()
        strDate?.let {
            var df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
            try {
                df.parse(it)?.let { d -> date = d }
            } catch (e: ParseException) {
                df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                df.parse(it)?.let { d -> date = d }
            }
        }
        return date
    }

    fun getLogDate(date: String): String {
        if (date.isEmpty()) {
            return date
        }
        var dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        Log.i("DateUtilsTAG0", "getDate: data$date")
        var strDate: String = date
        var d: Date? = null
        if (date.isNotEmpty()) {
            try {
                d = dateFormat.parse(date)
            } catch (e: ParseException) {
            }
        }
        if (d != null) {
            dateFormat.timeZone = TimeZone.getDefault()
            dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH)
            strDate = dateFormat.format(d)
        }
        return strDate
    }

    fun getPickerDate(date: String): String {
        if (date.isEmpty()) {
            return date
        }
        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        Log.i("DateUtilsTAG0", "getDate: data$date")
        var strDate: String = date
        var d: Date? = null
        if (date.isNotEmpty()) {
            try {
                d = dateFormat.parse(date)
            } catch (e: ParseException) {
            }
        }
        if (d != null) {
            dateFormat.timeZone = TimeZone.getDefault()
            dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH)

//            dateFormat = if (lang.equals("ar")) {
//                SimpleDateFormat(
//                    "yyyy/MM/dd hh:mm:ss", Locale(
//                        "ar"
//                    )
//                )
//            } else {
//                SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH)
//            }
            strDate = dateFormat.format(d)
        }
        return strDate
    }

    fun getChartDay(date: String): String {
        if (date.isEmpty()) {
            return date
        }
        var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var strDate: String = date
        var d: Date? = null
        if (date.isNotEmpty()) {
            try {
                d = dateFormat.parse(date)
            } catch (e: ParseException) {
            }
        }
        if (d != null) {
            dateFormat.timeZone = TimeZone.getDefault()
            dateFormat = if (lang.equals("ar")) {
                SimpleDateFormat(
                    "EEE", Locale(
                        "ar"
                    )
                )
            } else {
                SimpleDateFormat("EEE", Locale.ENGLISH)
            }
            strDate = dateFormat.format(d)
        }
        return strDate
    }

    fun getTransPickerDate(date: String): String {
        if (date.isEmpty()) {
            return date
        }
        var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var strDate: String = date
        var d: Date? = null
        if (date.isNotEmpty()) {
            try {
                d = dateFormat.parse(date)
            } catch (e: ParseException) {
            }
        }
        if (d != null) {
            dateFormat.timeZone = TimeZone.getDefault()
            dateFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
            strDate = dateFormat.format(d)
        }
        return strDate
    }

    fun getLastMonth(): ArrayList<String> {
        val aCalendar: Calendar = Calendar.getInstance()
        aCalendar.set(Calendar.DATE, 1)
        aCalendar.add(Calendar.DAY_OF_MONTH, -1)
        // aCalendar.set(Calendar.MILLISECOND, 999);
        val lastDateOfPreviousMonth: Date = aCalendar.time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        aCalendar.set(Calendar.DATE, 1)
        val firstDateOfPreviousMonth: Date = aCalendar.time
        val dateFrom = format.format(firstDateOfPreviousMonth)
        val dateTo = format.format(lastDateOfPreviousMonth)
        Log.e(TAG, "getLastMonth: ${dateFrom}")
        Log.e(TAG, "getLastMonth: $dateTo")
        val arr = ArrayList<String>()
        arr.add("$dateFrom 00:00")
        arr.add("$dateTo 23:59")
        return arr
    }

    fun getThisMonth(): ArrayList<String> {
        var format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)

        val aCalendar: Calendar = Calendar.getInstance()
        val lastDateOfPreviousMonth: Date = Calendar.getInstance().time
        aCalendar.set(Calendar.DATE, 1)
        val firstDateOfPreviousMonth: Date = aCalendar.time
        var dateTo = format.format(lastDateOfPreviousMonth)
        format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var dateFrom = format.format(firstDateOfPreviousMonth)
        Log.e(TAG, "getThisMonth: ${dateFrom}")
        Log.e(TAG, "getThisMonth: ${dateTo}")
        dateFrom = "$dateFrom 00:00"
        return compareDates(dateFrom, dateTo)
    }

    fun compareDates(date1: String, date2: String): ArrayList<String> {
        var isValid = false
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        var strDate: Date? = null
        var endDate: Date? = null

        try {
            strDate = sdf.parse(date1)
            endDate = sdf.parse(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (strDate != null && endDate != null) {
            if (endDate.after(strDate)) {
                isValid = true
            }
        }
        val arr = ArrayList<String>()

        if (isValid) {
            arr.add(date1)
            arr.add(date2)
        } else {
            arr.add(date2)
            arr.add(date1)
        }
        return arr
    }

    fun getLastSevenDays(): ArrayList<String> {
        val someDate = GregorianCalendar.getInstance()
        someDate.add(Calendar.DAY_OF_YEAR, -7)
        Log.e(TAG, "getLastMonth: ${someDate}")
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var dateFrom = format.format(someDate.time)
        Log.e(TAG, "getLastMonth: $dateFrom")
        var dateTo = getYesterdaysDate()[1]
        Log.e(TAG, "getLastMonth: $dateTo")
        dateFrom = "$dateFrom 00:00"
        dateTo = "$dateTo"
        return compareDates(dateFrom, dateTo)

    }

    fun getYesterdaysDate(): ArrayList<String> {
        val cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val format2 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var dateFrom = "${format2.format(cal.time)} 00:00"
        var dateTo = "${format2.format(cal.time)} 23:59"
        return compareDates(dateFrom, dateTo)
    }

    fun getTodaysDate(): ArrayList<String> {
        val cal = Calendar.getInstance();
        val format2 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        var dateFrom = "${format2.format(cal.time)} 00:00"
        var dateTo = format.format(cal.time)
        return compareDates(dateFrom, dateTo)

    }

    private fun getStartOfDay(date: Date): Date? {
        val calendar = Calendar.getInstance()
        val year = date.year
        val month = date.month
        val day = date.day
        calendar[year, month, day, 0, 0] = 0
        return calendar.time
    }

    private fun getEndOfDay(date: Date): Date? {
        val calendar = Calendar.getInstance()
        val year = date.year
        val month = date.month
        val day = date.day
        calendar[year, month, day, 23, 59] = 59
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.time
    }

    const val TAG = "DateUtils"

    fun getLocalizeStrDateFromSTR(dateStr: String): String {
        if (dateStr.isEmpty()) {
            return dateStr
        }
        var format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val newDate = format.parse(dateStr)

        val df = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        val date = df.format(newDate)
        return date
    }

    fun getDateBankHome(dateStr: String): String {
        if (dateStr.isEmpty()) {
            return ""
        }
        var format = SimpleDateFormat("dd/MM/yyyy, HH:mm:ss", Locale.ENGLISH)
        val newDate = format.parse(dateStr)
        if (newDate != null) {
            val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
            return df.format(newDate)
        }
        return dateStr
    }

    fun getTransDate(dateStr: String): String {
        if (dateStr.isEmpty()) {
            return ""
        }
        var format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        val newDate = format.parse(dateStr)
        if (newDate != null) {
            val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH)
            return df.format(newDate)
        }
        return dateStr
    }

}