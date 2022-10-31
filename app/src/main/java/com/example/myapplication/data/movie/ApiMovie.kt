package com.example.myapplication.data.movie

import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetail
import com.example.myapplication.model.MoviePlayNowResponse
import com.example.myapplication.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {

    @GET("movie/popular")
    suspend fun getMoviesPopular(@Query("page")page:Int): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Int): Movie

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(): MoviePlayNowResponse


}