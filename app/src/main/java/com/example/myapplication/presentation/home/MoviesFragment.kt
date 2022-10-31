package com.example.myapplication.presentation.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentMoviesBinding
import com.example.myapplication.extensions.collectLastestLyfeCycleFlow
import com.example.myapplication.extensions.showToast
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MoviePlayNow
import com.example.myapplication.presentation.adapter.LoadStateAdapter
import com.example.myapplication.presentation.adapter.MovieAdapter
import com.example.myapplication.presentation.adapter.MoviePageAdapter
import com.example.myapplication.presentation.adapter.MoviePosterAdapter
import com.example.myapplication.presentation.base.BaseFragment
import com.example.myapplication.presentation.commons.SpaceItemDecorationVertical
import com.example.myapplication.presentation.commons.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : BaseFragment<MoviesViewModel, FragmentMoviesBinding>
    (FragmentMoviesBinding::inflate) {
    override val vm: MoviesViewModel by viewModels()
    private var loading = false
    override val TAG: String = MoviesFragment::class.java.name
    private val movieAdapter by lazy { MovieAdapter() }
    private val moviePosterAdapter by lazy { MoviePosterAdapter() }
    private val moviePageAdapter by lazy { MoviePageAdapter{ movie: Movie ->
        val id = movie.id
        val action =
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(id.toInt())
        findNavController().navigate(action)

    } }
    private val itemClickPlayin = { movie: MoviePlayNow ->
        val id = movie.id
        val action =
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(id.toInt())
        findNavController().navigate(action)

    }


    override fun setupUI() {
        super.setupUI()
        binding.vm = vm
        initRecyclerview()
    }


    private fun initRecyclerview() {
        moviePageAdapter.withLoadStateFooter(footer = LoadStateAdapter())
        binding.recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(8)
            addItemDecoration(SpaceItemDecorationVertical(32))
            adapter = moviePageAdapter
        }
        binding.recyclerOne.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(8)
            adapter = moviePosterAdapter
        }



    }


    override fun setupVM() {
        super.setupVM()
        //vm.getMovies()
        vm.getMoviesPosters()
        //observerMovies()
        observerMoviesPage()
        observerPostersMovies()


    }


    private fun observerMoviesPage() {
        requireActivity().collectLastestLyfeCycleFlow(vm.movies) {
            moviePageAdapter.submitData(it)

        }
    }


    private fun observerMovies() {
        requireActivity().collectLastestLyfeCycleFlow(vm.moviesFlow) { uiState ->
            binding.itemProgressBar.visibility = View.GONE
            when (uiState) {
                is UIState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val movies = (uiState.data as List<Movie>)
                    movieAdapter.set(movies)
                    //movieAdapter.onItemClicked = itemClick
                }
                is UIState.Error -> {
                    uiState.message?.let { message ->
                        requireActivity().showToast(message)
                    }
                }
                else -> {}
            }

        }
    }

    private fun observerPostersMovies() {
        requireActivity().collectLastestLyfeCycleFlow(vm.moviesPostersFlow) { uiState ->
            when (uiState) {
                is UIState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val movies = (uiState.data as List<MoviePlayNow>)

                   moviePosterAdapter.set(movies)
                   moviePosterAdapter.onItemClicked = itemClickPlayin

                }
                is UIState.Error -> {
                    uiState.message?.let { message ->
                        requireActivity().showToast(message)
                    }
                }
                else -> {}
            }
        }
    }


}