package com.example.myapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieAndDetail
import com.example.myapplication.model.MoviePlayNow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviePopular(): Flow<List<Movie>>

    fun getMoviePopularPage(): Flow<PagingData<Movie>>

    fun getMoviePlayNow(): Flow<List<MoviePlayNow>>

    fun findMovieById(id: Int): Flow<Movie>

    suspend fun insertMovies(movies: List<Movie>)

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteMovies(movies: List<Movie>)

    fun findMovieAndDetailById(id: Int): Flow<Movie>


}