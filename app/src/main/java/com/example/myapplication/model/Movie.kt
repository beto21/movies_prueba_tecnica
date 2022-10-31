package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Movie.TABLE_NAME)
 data class Movie(
    @PrimaryKey
    override val id:Long): MovieBase() {
    var valoration:Int= 0

    companion object {
        const val TABLE_NAME = "movie"
    }


}