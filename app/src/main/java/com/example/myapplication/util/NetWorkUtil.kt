package com.example.myapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetWorkUtil(context: Context){
    private val connectivityManager  = context.applicationContext.
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetwordAvailable(): Boolean{
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        val hasInternet =
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        val isConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)

        return hasInternet && isConnected
    }
}