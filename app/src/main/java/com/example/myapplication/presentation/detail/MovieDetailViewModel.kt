package com.example.myapplication.presentation.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.error.ResourceNotFoundException
import com.example.myapplication.presentation.base.BaseViewModel
import com.example.myapplication.presentation.commons.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : BaseViewModel() {

    private val _movieFlow by lazy { MutableStateFlow<UIState>(UIState.Empty) }
    val movieFlow: StateFlow<UIState> = _movieFlow


    fun getmovie(id: Int) {
        viewModelScope.launch {
            movieRepository.findMovieAndDetailById(id)
                .catch {
                    val error: String = if (it is ResourceNotFoundException) {
                        "No se encontraron datos"
                    } else {
                        it.message!!
                    }
                    UIState.Error(error)
                    Log.e(TAG, error, it)
                    _movieFlow.value = UIState.Error(error)
                }.collect {
                    _movieFlow.value = UIState.Success(it)
                }
        }
    }


}