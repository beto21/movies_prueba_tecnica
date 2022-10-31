package com.example.myapplication.di

import com.example.myapplication.data.movie.ApiMovie
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.data.repository.impl.MovieRepositoryImpl
import com.example.myapplication.data.room.AppDb
import com.santander.globile.superherotest.utils.network.ConnectionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryMovie(
        db: AppDb,
        api: ApiMovie,
        connectionProvider: ConnectionProvider,

        ): MovieRepository {
        return MovieRepositoryImpl(api, db, connectionProvider)
    }

}