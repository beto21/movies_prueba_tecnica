package com.example.myapplication.presentation.viewholder

import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.model.Movie
import com.example.myapplication.presentation.base.BaseViewHolder

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    override val onClick: (data: Int) -> Unit
) : BaseViewHolder<Movie>(binding) {

    override fun bindTo(data: Movie) {
        binding.movie = data
        binding.executePendingBindings()
    }


}