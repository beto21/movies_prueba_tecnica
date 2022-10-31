package com.example.myapplication.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(
    binding: ViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    abstract val onClick: (data: Int) -> Unit

    abstract fun bindTo(data: T)

    init {
        itemView.setOnClickListener { onClick(adapterPosition) }
    }
}