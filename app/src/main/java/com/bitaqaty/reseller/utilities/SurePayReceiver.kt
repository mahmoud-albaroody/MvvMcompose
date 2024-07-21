package com.bitaqaty.reseller.utilities

import android.content.BroadcastReceiver
import android.content.Context
import com.bitaqaty.reseller.ui.delegate.GetLastStatusCallback
import android.content.Intent
import android.widget.Toast
import com.bitaqaty.reseller.data.model.MadaResponse
import com.bitaqaty.reseller.data.model.MadaResponseModel
import com.google.gson.Gson
import java.lang.NumberFormatException

class SurePayReceiver(
    private val callback: GetLastStatusCallback? = null
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val hasResponse = intent.extras!!.getBoolean("hasResponseReceipt")
        val requestType = intent.extras!!.getInt("requestType")
        val result = intent.extras!!.getString("RESULT")
        val signature = intent.extras!!.getString("SIGNATURE")
        if (result == null) {
            callback?.onLastStatusReceived(null)
        }
        if (isNumeric(result)) {
            when (result?.toInt()) {
                -56 -> Toast.makeText(context, "No reports for reverse trx ", Toast.LENGTH_LONG)
                    .show()
                -60 -> Toast.makeText(context, "reverse trx cancelled ... ", Toast.LENGTH_LONG)
                    .show()
                3001 -> Toast.makeText(context, "No report for last trx ... ", Toast.LENGTH_LONG)
                    .show()
            }
        }
        else {
            if (hasResponse) {
                when (requestType) {
                    0 -> {
                        val topic = Gson().fromJson(
                            result,
                            MadaResponseModel::class.java
                        )
                        if (topic != null) {
                            callback?.onLastStatusReceived(
                                MadaResponse(
                                    result!!,
                                    signature!!,
                                    topic
                                )
                            )
                        }
                    }
                    1 -> {
                        val topic = Gson().fromJson(
                            result,
                            MadaResponse::class.java
                        )
                        if (topic != null) {
                            callback?.onLastStatusReceived(topic)
                        }
                    }
                }
            } else {
                Toast.makeText(context, "No reports... ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isNumeric(str: String?): Boolean {
        return try {
            str?.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}