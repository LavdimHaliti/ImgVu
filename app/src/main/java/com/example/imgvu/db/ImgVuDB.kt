package com.example.imgvu.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imgvu.data.ImageDetail

@Database(entities = [ImageDetail::class], version = 1)
@TypeConverters(Converters::class)
abstract class ImgVuDB : RoomDatabase(){

    abstract fun imgVuDAO(): ImgVuDao
}