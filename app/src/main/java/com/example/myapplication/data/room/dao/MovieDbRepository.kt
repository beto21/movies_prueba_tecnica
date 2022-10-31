package com.example.myapplication.data.room.dao

import com.example.myapplication.model.Movie

interface MovieDbRepository {

     fun findMovieById(id: Int): Movie?

    fun insertMovie(movie: Movie)

    fun insertMovies(movies: List<Movie>)

    fun findMovies(): List<Movie>

    fun updateMovie(movie: Movie)

    fun deleteMovie(movie: Movie)

    fun deleteMovies(movies: List<Movie>)


}