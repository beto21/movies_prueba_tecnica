package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

abstract class MovieBase{
    abstract val id:Long
    var adult: Boolean? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("original_title")
    var originalTitle: String? = null
    var overview: String? = null
    var popularity: Double? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("release_date")
    var releaseDate: String? = null
    var title: String? = null
    var video: Boolean? = null
    @SerializedName("vote_average")
    var voteAverage: Double? = null
    @SerializedName("vote_count")
    var voteCount: Int? = null
    var url: String? = ""

}