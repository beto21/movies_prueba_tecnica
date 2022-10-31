package com.example.myapplication.data.room.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>)

    @Update
    fun update(entity: T)

    @Update
    fun updateAll(entities: List<T>)

    @Delete
    fun delete(entity: T)

    @Delete
    fun delete(entities: List<T>)

}