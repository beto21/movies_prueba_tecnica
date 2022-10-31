package com.santander.globile.superherotest.utils.network

import kotlin.random.Random


sealed class NetworkState {
    object NoConnected : NetworkState()
    data class Connected(val connect: Boolean) : NetworkState()
    object Loading : NetworkState()


    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return Random.nextInt()
    }
}