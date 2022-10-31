package com.example.myapplication.presentation.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.MovieDetailFragmentBinding
import com.example.myapplication.extensions.collectLastestLyfeCycleFlow
import com.example.myapplication.model.Movie
import com.example.myapplication.presentation.base.BaseFragment
import com.example.myapplication.presentation.commons.UIState
import com.example.myapplication.model.MovieAndDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<MovieDetailViewModel, MovieDetailFragmentBinding>(MovieDetailFragmentBinding::inflate) {

    override val vm: MovieDetailViewModel by viewModels()
    private val args by navArgs<MovieDetailFragmentArgs>()
    override val TAG: String = MovieDetailFragment::class.java.name

    override fun setupUI() {
        super.setupUI()
        binding.vm = vm
        vm.getmovie(args.id)

    }

    override fun setupVM() {
        super.setupVM()
        requireActivity().collectLastestLyfeCycleFlow(vm.movieFlow) { uiState ->
            when (uiState) {
                is UIState.Success<*> -> {
                    binding.movie = uiState.data as Movie
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG)
                        .show()
                }
                else -> {}
            }


        }


    }

}