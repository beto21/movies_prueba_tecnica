package com.example.myapplication.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.error.ResourceNotFoundException
import com.example.myapplication.model.Movie
import com.example.myapplication.presentation.base.BaseViewModel
import com.example.myapplication.presentation.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {


    private val _moviesFlow = MutableStateFlow<UIState>(UIState.Empty)
    val moviesFlow: StateFlow<UIState> = _moviesFlow.asStateFlow()

    val movies: Flow<PagingData<Movie>> = movieRepository.getMoviePopularPage()
        .cachedIn(viewModelScope).flowOn(Dispatchers.IO)

    private val _moviesPostersFlow = MutableStateFlow<UIState>(UIState.Empty)
    val moviesPostersFlow: StateFlow<UIState> = _moviesPostersFlow.asStateFlow()

    fun getMovies() {
        viewModelScope.launch {
            movieRepository.getMoviePopular().catch {
                if (it !is ResourceNotFoundException) {
                    _moviesFlow.value = UIState.Error(it.message)
                }
            }.collectLatest {
                _moviesFlow.value = UIState.Success(it)

            }
        }
    }



    fun getMoviesPosters() {
        viewModelScope.launch {
            movieRepository.getMoviePlayNow().catch {
                if (it !is ResourceNotFoundException) {
                    _moviesPostersFlow.value = UIState.Error(it.message)
                }
            }.collect {
                _moviesPostersFlow.value = UIState.Success(it)

            }
        }
    }


}