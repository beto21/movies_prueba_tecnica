package com.example.myapplication.presentation.commons

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = recyclerView.childCount
        val totalItemCount: Int = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem: Int =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (loading && totalItemCount > mPreviousTotal) {
            loading = false
            mPreviousTotal = totalItemCount
        }
        val visibleThreshold = 5
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            onLoadMore()
            loading = true
        }
    }

    abstract fun onLoadMore()


}