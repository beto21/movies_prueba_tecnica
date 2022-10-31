package com.example.myapplication.presentation.viewholder

import com.example.myapplication.databinding.ItemPortadaBinding
import com.example.myapplication.presentation.base.BaseViewHolder
import com.example.myapplication.model.MoviePlayNow

class MoviePostherViewHolder(
    private val binding: ItemPortadaBinding,
    override val onClick: (data: Int) -> Unit) : BaseViewHolder<MoviePlayNow>(binding) {
    override fun bindTo(data: MoviePlayNow) {
        binding.movie = data
    }
}