package com.example.myapplication.di

import android.app.Application
import android.util.Log
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.movie.ApiMovie
import com.example.myapplication.error.NetworkException
import com.example.myapplication.utils.ErrorUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitHero(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiMovieService(retrofit: Retrofit): ApiMovie =
        retrofit.create(ApiMovie::class.java)


    @Provides
    @Named("autho")
    fun provideAthorizationInterceptor(): Interceptor {
        return Interceptor {
            var request: Request = it.request()
            val url = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            request = request.newBuilder().url(url).build()
            it.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Named("logging")
    fun provideLoggingInterceptors(): Interceptor {
        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = HttpLoggingInterceptor.Level.BODY
        return interceptorLogger
    }
    @Provides
    @Named("error")
    fun provideErrorInterceptor(): Interceptor {
        return Interceptor {
            try {
                val request: Request = it.request()
                val response: Response = it.proceed(request)
                if (response.code / 100 == 5) {
                    val error = ErrorUtils.getResourceError(request, response)
                    Log.w("Resources", "Error en el servidor: $error")
                }
                response
            } catch (e: IOException) {
                throw NetworkException("No cuentas con conexón de red", e)

            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpCache: Cache,
        @Named("logging") loggingInterceptor: Interceptor,
        @Named("autho") authoInterceptor: Interceptor,
        @Named("error") errorInterceptor: Interceptor

    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(BuildConfig.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .cache(okHttpCache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authoInterceptor)
            .addInterceptor(errorInterceptor)
        return builder.build()
    }

}