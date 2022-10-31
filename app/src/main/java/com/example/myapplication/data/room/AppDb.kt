package com.example.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.room.converter.GenresConverter
import com.example.myapplication.data.room.dao.MovieAndDetailDao
import com.example.myapplication.data.room.dao.MovieDao
import com.example.myapplication.data.room.dao.MovieDetailDao
import com.example.myapplication.data.room.dao.MoviePlayNowDao
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetail
import com.example.myapplication.model.MoviePlayNow

@Database(
    entities =
    [Movie::class,
        MovieDetail::class,
        MoviePlayNow::class ], version = 1, exportSchema = false
)

@TypeConverters(
    GenresConverter::class
)
abstract class AppDb :RoomDatabase(){
    abstract val movieDao: MovieDao
    abstract val movieDetailDao: MovieDetailDao
    abstract val movieAndDetailDao: MovieAndDetailDao
    abstract val moviePlayNowDao:MoviePlayNowDao
}