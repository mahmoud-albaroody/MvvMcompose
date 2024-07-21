package com.bitaqaty.reseller.utilities

import android.content.Context
import com.bitaqaty.reseller.BuildConfig
import io.nearpay.sdk.Environments
import io.nearpay.sdk.NearPay
import io.nearpay.sdk.utils.PaymentText
import io.nearpay.sdk.utils.enums.AuthenticationData
import io.nearpay.sdk.utils.enums.NetworkConfiguration
import io.nearpay.sdk.utils.enums.UIPosition
import java.util.*

 fun Context.initCashIn(): NearPay {
     if(BuildConfig.APPLICATION_ID ==  "com.bitaqaty.resellerStaging") {
         return NearPay.Builder()
             .context(this)
             .authenticationData(AuthenticationData.Mobile("+966533553122"))
             .environment(Environments.SANDBOX)
             .locale(Locale.getDefault())
             .networkConfiguration(NetworkConfiguration.SIM_PREFERRED)
             .uiPosition(UIPosition.CENTER_BOTTOM)
             .paymentText(PaymentText("يرجى تمرير الطاقة", "please tap your card"))
             .loadingUi(true)
             .build()
     }else{
         return NearPay.Builder()
             .context(this)
             .authenticationData(AuthenticationData.Mobile("+966532851968"))
             .environment(Environments.PRODUCTION)
             .locale(Locale.getDefault())
             .networkConfiguration(NetworkConfiguration.SIM_PREFERRED)
             .uiPosition(UIPosition.CENTER_BOTTOM)
             .paymentText(PaymentText("يرجى تمرير الطاقة", "please tap your card"))
             .loadingUi(true)
             .build()
     }


}