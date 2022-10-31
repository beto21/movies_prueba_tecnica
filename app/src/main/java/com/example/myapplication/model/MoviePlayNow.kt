package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MoviePlayNow.TABLE_NAME)
data class MoviePlayNow(
    @PrimaryKey
    override val id:Long
) : MovieBase() {

    companion object{
       const val TABLE_NAME = "MoviePlayNow"
    }
}