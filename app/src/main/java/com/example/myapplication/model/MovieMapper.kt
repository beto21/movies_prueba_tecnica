package com.example.myapplication.model

interface MovieMapper {

    suspend fun mapRemoteMoviesListToDomain(remoteMovies: List<MovieResponse>): List<Movie>

    suspend fun mapRemoteMovieToDomain(remoteMovie: MovieResponse): Movie

    suspend fun mapDomainMoviesListToUi(domainMovies: List<Movie>): List<Movie>

    suspend fun mapDomainMovieToUi(domainMovie: Movie): Movie


}