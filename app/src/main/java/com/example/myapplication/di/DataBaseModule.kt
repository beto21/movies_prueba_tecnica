package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.room.AppDb
import com.example.myapplication.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Named(DATABASE_NAME)
    @Singleton
    fun provideDatabaseName() = BuildConfig.DATABASE_NAME


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        @Named(DATABASE_NAME) databaseName: String
    ) = Room.databaseBuilder(app, AppDb::class.java, databaseName)
        .fallbackToDestructiveMigration()
        .build()


}