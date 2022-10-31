package com.example.myapplication.presentation.adapter

import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.presentation.base.BaseAdapter
import com.example.myapplication.presentation.viewholder.MovieViewHolder
import com.example.myapplication.model.Movie
import com.example.myapplication.presentation.base.BaseViewHolder

class MovieAdapter : BaseAdapter<Movie, ItemMovieBinding>(ItemMovieBinding::inflate) {

    override var onItemClicked: (data: Movie) -> Unit = {}

    override fun set(data: List<Movie>) {
        submitList(data)
    }

    override fun viewHolder(binding: ItemMovieBinding): BaseViewHolder<Movie> {
        return MovieViewHolder(binding) { onItemClicked(getItem(it)) }
    }
}