package com.example.myapplication.model

import androidx.room.Embedded
import androidx.room.Relation

data class MovieAndDetail(
    @Embedded var movie: Movie,
    @Relation(parentColumn = "id", entityColumn = "id")
    var movieDetail: MovieDetail? = null

)