package com.example.myapplication.data.repository.impl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.movie.ApiMovie
import com.example.myapplication.data.movie.MoviePagingSource
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.data.room.AppDb
import com.example.myapplication.error.ResourceNotFoundException
import com.example.myapplication.utils.ORIGINAL
import com.example.myapplication.utils.SIZE_W500
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MoviePlayNow
import com.santander.globile.superherotest.utils.network.ConnectionProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.math.roundToInt

class MovieRepositoryImpl(
    private val api: ApiMovie,
    private val db: AppDb,
    private val connectionProvider: ConnectionProvider,
) : MovieRepository {

    companion object {
        private val TAG = MovieRepositoryImpl::class.java.name
    }

    override fun getMoviePopular() = flow {
        try {
            var movies: List<Movie> = db.movieDao.findAll()
            if (movies.isEmpty()) {
                connectionProvider.isConnected()?.let { connected ->
                    if (connected) {
                        movies = api.getMoviesPopular(1).results
                        movies.forEach { movie ->
                            movie.url = "${BuildConfig.BASE_URL_IMAGE}$ORIGINAL${movie.posterPath}"
                            movie.valoration = (movie.voteAverage!!.toDouble() * 10).roundToInt()
                        }
                        db.movieDao.insert(movies)
                        emit(movies)
                    } else {
                        throw ResourceNotFoundException()
                    }
                }

            } else {
                emit(movies)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ocurrio un error: ", e)
            throw e
        }
    }.flowOn(Dispatchers.IO)

    override fun getMoviePopularPage(): Flow<PagingData<Movie>> {
        val pagingConfig = PagingConfig(pageSize = 1)
        val pager = Pager(config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(api)
            }, initialKey = 1
        )
        return pager.flow
    }

    override fun getMoviePlayNow(): Flow<List<MoviePlayNow>> = flow {
        try {
            var movies: List<MoviePlayNow> = db.moviePlayNowDao.findAll()
            if (movies.isEmpty()) {
                connectionProvider.isConnected()?.let { connected ->
                    if (connected) {
                        movies = api.getMoviesNowPlaying().results
                        movies.forEach { movie ->
                            movie.url = "${BuildConfig.BASE_URL_IMAGE}$SIZE_W500${movie.posterPath}"
                        }
                        db.moviePlayNowDao.insert(movies)
                        emit(movies)
                    } else {
                        throw ResourceNotFoundException()
                    }
                }

            } else {
                emit(movies)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ocurrio un error: ", e)
            throw e
        }
    }

    override fun findMovieById(id: Int) = flow {
        emit(db.movieDao.findById(id)!!)
    }.flowOn(Dispatchers.IO)


    override suspend fun insertMovies(movies: List<Movie>) {
        db.movieDao.insert(movies)
    }

    override suspend fun insertMovie(movie: Movie) {
        db.movieDao.insert(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        db.movieDao.delete(movie)
    }

    override suspend fun deleteMovies(movies: List<Movie>) {
        db.movieDao.delete(movies)
    }

    override fun findMovieAndDetailById(id: Int): Flow<Movie> = flow {

        val moviDetail = api.getMovieDetail(id)
        moviDetail.url = "${BuildConfig.BASE_URL_IMAGE}$SIZE_W500${moviDetail.posterPath}"
        emit(moviDetail)


    }.flowOn(Dispatchers.IO)


}