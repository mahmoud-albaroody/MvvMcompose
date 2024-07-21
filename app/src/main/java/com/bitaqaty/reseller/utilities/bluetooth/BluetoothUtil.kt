package com.bitaqaty.reseller.utilities.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import java.io.IOException
import java.io.OutputStream
import java.util.*

object BluetoothUtil {
    private val PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var Innerprinter_Address = "00:11:22:33:44:55"
    var isBlueToothPrinter = false
    private var bluetoothSocket: BluetoothSocket? = null
    private val bTAdapter: BluetoothAdapter?
        private get() = BluetoothAdapter.getDefaultAdapter()

    private fun getDevice(bluetoothAdapter: BluetoothAdapter?): BluetoothDevice? {
        var innerprinter_device: BluetoothDevice? = null
        val devices = bluetoothAdapter!!.bondedDevices
        for (device in devices) {
            if (device.address == Innerprinter_Address) {
                innerprinter_device = device
                break
            }
        }
        return innerprinter_device
    }

    @Throws(IOException::class)
    private fun getSocket(device: BluetoothDevice): BluetoothSocket {
        val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(PRINTER_UUID)
        socket.connect()
        return socket
    }

    fun connectBlueToothQuietly(): Boolean {
        if (bluetoothSocket == null) {
            if (bTAdapter == null) {
                return false
            }
            if (!bTAdapter!!.isEnabled) {
                return false
            }
            try {
                var device: BluetoothDevice
                if (getDevice(bTAdapter).also { device = it!! } == null) {
                    return false
                }
                bluetoothSocket = getSocket(device)
            } catch (e: Exception) {
                return false
            }
        }
        isBlueToothPrinter = true
        return true
    }

    /**
     * disconnect bluethooth
     */
    fun disconnectBlueTooth(context: Context?) {
        if (bluetoothSocket != null) {
            try {
                val out = bluetoothSocket!!.outputStream
                out.close()
                bluetoothSocket!!.close()
                bluetoothSocket = null
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * send esc cmd
     */
    fun sendData(bytes: ByteArray) {
        if (bluetoothSocket != null) {
            var out: OutputStream? = null
            try {
                out = bluetoothSocket!!.outputStream
                out.write(bytes, 0, bytes.size)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            //TODO handle disconnect event
        }
    }
}
