package com.example.myapplication.util

import android.content.Context import android.net.ConnectivityManager import android.net.NetworkCapabilities
import androidx.work.impl.constraints.trackers.NetworkStateTracker

//class NetworkUtil(context: Context) {
//    // Dùng applicationContext để tránh leak
//    private val connectivityManager =
//        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    /**
//     * Kiểm tra xem thiết bị có mạng Internet khả dụng hay không
//     */
//    fun isNetworkAvailable(): Boolean {
//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//        // Kiểm tra network có khả năng Internet và transport phổ biến
//        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
//                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
//                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
//    }
//}


class NetworkUtil(val context: Context){
    private val connectivityManager = context.applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

   suspend fun isNetworkAvailable(): Boolean{
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network)?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

}



























//class NetworkUtil(private val context: Context) {
//    fun isNetworkAvailable(): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network)
//            ?: return false
//        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//    }
//}

