package com.example.myapplication.presentation.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T, VB>(private val inflate: Inflate<VB>)
    : ListAdapter<T, BaseViewHolder<T>>(BaseItemCallback<T>()) {

    abstract val onItemClicked: (data: T) -> Unit

    abstract fun set(data: List<T>)

    protected abstract fun viewHolder(binding: VB): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = inflate.invoke(layoutInflater, parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindTo(getItem(position))
    }

    class BaseItemCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

    }
}