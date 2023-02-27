package com.example.nhentai

import android.os.StrictMode
import android.util.Log
import timber.log.Timber
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//=====================================================
// Отправить Udp сообщение * Возвращает OK или ошибку
// region // sendUDP(messageStr: String, ip :String, port: Int): String
fun sendUDP(messageStr: String, ip: String, port: Int): String {
    // Hack Prevent crash (sending should be done using an async task)
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    try {
        val socket = DatagramSocket()
        socket.broadcast = true

        val sendData = messageStr.toByteArray()
        val sendPacket =
            DatagramPacket(sendData, sendData.size, InetAddress.getByName(ip), port)
        socket.send(sendPacket)
        println("sendUDP: $ip:$port")
    } catch (e: IOException) {
        Log.e("sendUDP", "IOException: " + e.message)
        return e.message.toString()
    }
    return "OK"
}

//data class RemoteLog(
//    var priority: String,
//    var tag: String?,
//    var message: String,
//    var throwable: String?,
//    val time: String
//)

class TimberRemoteTree() : Timber.DebugTree() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS a zzz", Locale.getDefault())
    private val date = dateFormat.format(Date(System.currentTimeMillis()))

    //private val logRef = Firebase.database.getReference("logs/$date/${deviceDetails.deviceId}")

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        if (BuildConfig.DEBUG) {
            val timestamp = System.currentTimeMillis()
            val time = timeFormat.format(Date(timestamp))

            sendUDP(message, "192.168.0.100", 8888)

            //val remoteLog = RemoteLog(priorityAsString(priority), tag, message, t.toString(), time)

//            with(logRef) {
//                updateChildren(mapOf(Pair("-DeviceDetails", deviceDetails)))
//                child(timestamp.toString()).setValue(remoteLog)
//            }
        } else
            super.log(priority, tag, message, t)
    }

    private fun priorityAsString(priority: Int): String = when (priority) {
        Log.VERBOSE -> "VERBOSE"
        Log.DEBUG -> "DEBUG"
        Log.INFO -> "INFO"
        Log.WARN -> "WARN"
        Log.ERROR -> "ERROR"
        Log.ASSERT -> "ASSERT"
        else -> priority.toString()
    }
}

