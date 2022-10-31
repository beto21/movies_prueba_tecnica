package com.example.myapplication.utils.network.base

import android.os.Handler
import android.os.Looper
import com.santander.globile.superherotest.utils.network.ConnectionProvider
import com.santander.globile.superherotest.utils.network.ConnectivityStateListener
import com.santander.globile.superherotest.utils.network.NetworkState

abstract class ConnectivityBase : ConnectionProvider {

    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<ConnectivityStateListener>()
    private var isSubscribed = false
    private var isConnection: NetworkState? = null

    protected abstract fun subscribe()
    protected abstract fun unsubscribe()

    override fun addListener(listener: ConnectivityStateListener) {
        listeners.add(listener)
        listener.onStateChange(networkState())
        verifySubscription()

    }

    override fun removeListener(listener: ConnectivityStateListener) {
        listeners.remove(listener)
        verifySubscription()
    }

    private fun verifySubscription() {
        if (!isSubscribed && listeners.isNotEmpty()) {
            subscribe()
            isSubscribed = true
        } else if (isSubscribed && listeners.isEmpty()) {
            unsubscribe()
            isSubscribed = false
        }
    }


    protected fun updateState(state: NetworkState) {
        handler.post {
            for (listener in listeners) {
                isConnection = state
                listener.onStateChange(state)
            }
        }
    }


}