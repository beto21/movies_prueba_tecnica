package com.example.myapplication.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieAndDetail
import com.example.myapplication.model.MovieDetail
import com.example.myapplication.model.MoviePlayNow

@Dao
interface MovieDao : BaseDao<Movie>{

    @Transaction
    @Query("SELECT * FROM movie ")
    suspend fun findAll(): List<Movie>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Movie?
}

@Dao
interface MoviePlayNowDao : BaseDao<MoviePlayNow>{

    @Transaction
    @Query("SELECT * FROM MoviePlayNow ")
    suspend fun findAll(): List<MoviePlayNow>

    @Transaction
    @Query("SELECT * FROM MoviePlayNow WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): MoviePlayNow?
}

@Dao
interface MovieDetailDao : BaseDao<MovieDetail>{

    @Transaction
    @Query("SELECT * FROM movieDetail ")
    suspend fun findAll(): List<MovieDetail>

    @Transaction
    @Query("SELECT * FROM movieDetail WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): MovieDetail?
}

@Dao
interface MovieAndDetailDao {
    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id LIMIT 1")
    fun findById(id: Int): MovieAndDetail?


}

