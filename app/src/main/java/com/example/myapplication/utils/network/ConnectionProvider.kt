package com.santander.globile.superherotest.utils.network

import kotlinx.coroutines.flow.Flow


interface ConnectionProvider {
    fun addListener(listener: ConnectivityStateListener)
    fun removeListener(listener: ConnectivityStateListener)
    fun networkState(): NetworkState
    fun isConnected(): Boolean?
    fun onState(): Flow<NetworkState>


}

