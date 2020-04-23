package com.example.eattendace.NetworkManager

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
object AppUtil {
    fun isOnline(context: Context): Boolean {
        return try {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfoMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            val netInfoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            netInfoMobile != null && netInfoMobile.isConnectedOrConnecting ||
                    netInfoWifi != null && netInfoWifi.isConnectedOrConnecting
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}