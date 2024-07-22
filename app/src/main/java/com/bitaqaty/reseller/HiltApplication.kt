package com.bitaqaty.reseller

import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.MySharedPreferences
import com.bitaqaty.reseller.utilities.SurePayReceiver
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : MultiDexApplication() {
    companion object {
        private const val LIVECHAT_SUPPORT_LICENCE_NR = "8646654"
        var myReceiver: SurePayReceiver? = null
        var gson = Gson()
        var mPrefs: MySharedPreferences? = null
        private var applicationInstance: HiltApplication? = null

    }
    override fun onCreate() {
        super.onCreate()
        mPrefs = MySharedPreferences.getInstance(
            this,
            this.packageName + Globals.SHARED_PREFS
        )
        gson = Gson()
        applicationInstance = this


    }
}