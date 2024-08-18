package com.bitaqaty.reseller

import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.multidex.MultiDexApplication
import com.bitaqaty.reseller.data.localStorage.BagDatabase
import com.bitaqaty.reseller.data.localStorage.getDatabase
import com.bitaqaty.reseller.data.model.MadaResponse
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.MySharedPreferences
import com.bitaqaty.reseller.utilities.SunmiPrintHelper
import com.bitaqaty.reseller.utilities.SurePayReceiver
import com.google.gson.Gson
import com.iovation.mobile.android.FraudForceConfiguration
import com.iovation.mobile.android.FraudForceManager
import com.livechatinc.inappchat.ChatWindowConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : MultiDexApplication() {

    lateinit var database: BagDatabase

    companion object {
        private const val LIVECHAT_SUPPORT_LICENCE_NR = "8646654"
        var myReceiver: SurePayReceiver? = null
        var gson = Gson()
        var mPrefs: MySharedPreferences? = null
        var result: String? = null

        private var applicationInstance: MainApplication? = null

        @Synchronized
        fun getInstance(): MainApplication? {
            return applicationInstance
        }

//        var sureCallback: SureCallback?=null
//        fun getCallBackInstance(callback: SureCallback) {
//            sureCallback = callback
//        }

        var networkCallBack: (() -> Unit?)? = null
        fun networkCallBackInstance(callback:() -> Unit) {
            networkCallBack = callback
        }

        fun getChatWindowConfiguration(
            email: String,
            name: String,
            phone: String
        ): ChatWindowConfiguration {
            return ChatWindowConfiguration(LIVECHAT_SUPPORT_LICENCE_NR, null, name, email, null)
        }
    }

    override fun onCreate() {
        mPrefs = MySharedPreferences.getInstance(
            this,
            this.packageName + Globals.SHARED_PREFS
        )
        gson = Gson()
        applicationInstance = this
     //   PicassoUtils.initPicasso(this)
        database = getDatabase(this)
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this)
//        if (myReceiver == null) {
//            myReceiver = SurePayReceiver(callback = this)
//            val filter = IntentFilter()
//            filter.addAction("surepay.mada.RESULT")
//            registerReceiver(
//                myReceiver,
//                filter
//            )
//        }

        val configuration = FraudForceConfiguration.Builder()
            .subscriberKey("M1WrRSwcjUBQmHamij3DxQJWr00YzfRhXaMkI+zhhiY=")
            .enableNetworkCalls(true)
            .build()
//
        FraudForceManager.initialize(configuration, applicationContext)

        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID);
//
//        // promptForPushNotifications will show the native Android notification permission prompt.
//        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//        OneSignal.promptForPushNotifications()
        super.onCreate()
    }


//    override fun onLastStatusReceived(madaResponse: MadaResponse?) {
//        sureCallback?.sureCallback(madaResponse)
//    }


}