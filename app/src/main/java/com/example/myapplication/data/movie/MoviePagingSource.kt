package com.example.myapplication.data.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.BuildConfig
import com.example.myapplication.model.Movie
import com.example.myapplication.utils.SIZE_W500
import kotlin.math.roundToInt

class MoviePagingSource(
    private val movieApiService: ApiMovie
): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response = movieApiService.getMoviesPopular(nextPage)
            response.results.forEach { movie ->
                movie.url = "${BuildConfig.BASE_URL_IMAGE}$SIZE_W500${movie.posterPath}"
                movie.valoration = (movie.voteAverage!!.toDouble() * 10).roundToInt()}
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}