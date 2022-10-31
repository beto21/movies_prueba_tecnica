package com.santander.globile.superherotest.utils.network.impl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.santander.globile.superherotest.utils.network.ConnectivityStateListener
import com.santander.globile.superherotest.utils.network.NetworkState
import com.example.myapplication.utils.network.base.ConnectivityBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive

@ExperimentalCoroutinesApi
@Suppress("DEPRECATION")
class ConnectionProviderImpl(
    private val context: Context,
    private val connectivityManager: ConnectivityManager
) : ConnectivityBase() {
    private val networkCallback = ConnectivityCallback()
    private val receiver = ConnectivityReceiver()

    private var connection = false


    companion object {
        val TAG = ConnectionProviderImpl::class.java.name


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getNetworkCapabilities(): NetworkCapabilities? {
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    }

    private fun getNetWorkInfo(): NetworkInfo? {
        return connectivityManager.activeNetworkInfo
    }


    override fun subscribe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)

        } else {
            context.registerReceiver(
                receiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun unsubscribe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(receiver)
        }


    }


    override fun networkState(): NetworkState {
        val network = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getNetworkCapabilities() else getNetWorkInfo()
        return validateNetwork(network)

    }


    override fun isConnected(): Boolean? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getNetworkCapabilities()?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            getNetWorkInfo()?.isConnectedOrConnecting
        }
    }

    override fun onState(): Flow<NetworkState> = callbackFlow {
        val listener = object : ConnectivityStateListener {
            override fun onStateChange(state: NetworkState) {
                val stateConnection: Boolean = (state as? NetworkState.Connected)?.connect ?: false
                if (connection != stateConnection) {
                    connection = stateConnection

                    if (isActive) {
                        trySend(state).onClosed {
                            Log.e(TAG, "closed:", it)
                            // cancel("Error", it)
                        }.onFailure {
                            Log.e(TAG, "error:", it)
                            // cancel("Error", it)
                        }.onSuccess { value ->
                            Log.e(TAG, "Succes: $value.")
                        }
                    }
                }
            }
        }
        addListener(listener)

        awaitClose {
            removeListener(listener)

        }
    }.flowOn(Dispatchers.IO)


    private fun <T> validateNetwork(networkconnection: T): NetworkState {
        var connection: NetworkState = NetworkState.NoConnected
        networkconnection?.let { network ->
            if ((network is NetworkInfo
                        && network.isConnectedOrConnecting)
                || (network is NetworkCapabilities
                        && network.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            ) {
                connection = NetworkState.Connected(true)
            }
        }
        return connection
    }


    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val capabilities: NetworkCapabilities? = getNetworkCapabilities()
            val capability =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            if (capability) {
                updateState(NetworkState.Connected(true))
            }
        }


        override fun onLost(network: Network) {
            updateState(NetworkState.NoConnected)
        }
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    private inner class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val networkInfo = connectivityManager.activeNetworkInfo
            val connection = networkInfo?.isConnectedOrConnecting ?: false
            val state: NetworkState =
                if (connection) {
                    NetworkState.Connected(true)
                } else {
                    NetworkState.NoConnected
                }
            updateState(state)
        }

    }


}