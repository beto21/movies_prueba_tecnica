package com.example.myapplication.model

abstract class MovieResponseBase<T> {
    abstract val results: List<T>
    val page: Int = 0
    val total_pages: Int = 0
    val total_results: Int = 0
}
