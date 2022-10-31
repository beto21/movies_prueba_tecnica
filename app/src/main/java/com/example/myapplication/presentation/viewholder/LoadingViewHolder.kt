package com.example.myapplication.presentation.viewholder

import android.widget.ProgressBar
import com.example.myapplication.databinding.LoadStateViewBinding
import com.example.myapplication.presentation.base.BaseViewHolder


class LoadingViewHolder(val binding:LoadStateViewBinding) :BaseViewHolder<ProgressBar>(binding) {
    override val onClick: (data: Int) -> Unit = {}

    override fun bindTo(data: ProgressBar) {
        TODO("Not yet implemented")
    }

}