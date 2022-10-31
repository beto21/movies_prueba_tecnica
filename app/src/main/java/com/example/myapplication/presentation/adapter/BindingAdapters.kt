package com.example.myapplication.presentation.adapter

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.model.Genre
import com.example.myapplication.utils.UiUtils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("imageUrl")
fun setImageGlide(container: AppCompatImageView, url: String?) {
    Glide.with(container.context)
        .load(url ?: R.drawable.not_found_image)
        .placeholder(R.drawable.image_loader)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .error(R.drawable.not_found_image)
        .into(container)

}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    if (show) view.visibility = View.VISIBLE else view.visibility = View.GONE
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("color")
fun colorProgress(progress:CircularProgressIndicator, value: Int){
    if (value <= 50){
        progress.setIndicatorColor(progress.context.getColor(R.color.md_yellow_A400))
    }else{
        progress.setIndicatorColor(progress.context.getColor(R.color.md_green_A700))

    }

}

@BindingAdapter("items")
fun setItems(view: ChipGroup, genres: List<Genre?>?) {
    if (genres == null || view.childCount > 0) return

    val context = view.context
    for (genre in genres) {
        val chip = Chip(context)
        chip.text = genre!!.name
        chip.chipStrokeWidth = UiUtils.dipToPixels(context, 1f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chip.chipStrokeColor = ColorStateList.valueOf(
                context.resources.getColor(R.color.md_blue_grey_200, null)
            )
        }
        chip.setChipBackgroundColorResource(android.R.color.transparent)
        view.addView(chip)
    }
}