package com.bitaqaty.reseller.utilities.bluetooth

import android.os.Handler
import android.os.Message
import android.util.Log
import com.zj.btsdk.BluetoothService

class BluetoothHandler(private val hi: HandlerInterface) : Handler() {
    private var mService: BluetoothService? = null

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            BluetoothService.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                BluetoothService.STATE_CONNECTED -> hi.onDeviceConnected()
                BluetoothService.STATE_CONNECTING -> hi.onDeviceConnecting()
                BluetoothService.STATE_LISTEN, BluetoothService.STATE_NONE -> Log.i(TAG, "Bluetooth state listen or none")
            }
            BluetoothService.MESSAGE_CONNECTION_LOST -> hi.onDeviceConnectionLost()
            BluetoothService.MESSAGE_UNABLE_CONNECT -> hi.onDeviceUnableToConnect()
        }
    }

    interface HandlerInterface {
        fun onDeviceConnected()
        fun onDeviceConnecting()
        fun onDeviceConnectionLost()
        fun onDeviceUnableToConnect()
    }

    companion object {
        private val TAG = BluetoothHandler::class.java.simpleName
    }
}
