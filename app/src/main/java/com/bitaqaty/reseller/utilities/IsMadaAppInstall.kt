package com.bitaqaty.reseller.utilities

import CTOS.CtPrint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.View


fun isMadaAppInstalled(activity: Activity): Boolean {
    val pm = activity.packageManager
    return try {
        pm.getPackageInfo("com.surepay.mada", PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

@Suppress("DEPRECATION")
fun isMadaAppRunning(activity: Activity): Boolean {
    try {
        val manager =
            activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
             if ("com.surepay.mada.MadaIntegration.MadaIntegrationService" == service.service.className)
                 return true
        }
    } catch (ex: Exception) {

    }
    return false
}

fun doPrinting(view: View, print: CtPrint) {
    val image4 = getBitmapFromView(view)
    print.initPage(image4.height)
    print.drawImage(image4, 0, 0)
    print.printPage()
    print.roll(100)
}
