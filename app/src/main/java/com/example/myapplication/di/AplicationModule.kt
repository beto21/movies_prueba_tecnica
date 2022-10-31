package com.example.myapplication.di

import android.content.Context
import android.net.ConnectivityManager
import com.santander.globile.superherotest.utils.network.ConnectionProvider
import com.santander.globile.superherotest.utils.network.impl.ConnectionProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AplicationModule {

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    @ExperimentalCoroutinesApi
    @Provides
    @Singleton
    fun provideConnection(
        @ApplicationContext appContext: Context,
        connectivityManager: ConnectivityManager
    ): ConnectionProvider {
        return ConnectionProviderImpl(appContext, connectivityManager)

    }


}

