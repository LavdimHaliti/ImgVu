package com.example.imgvu.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.room.*
import com.example.imgvu.data.ImageDetail

@Dao
interface ImgVuDao {

    @Query("SELECT * FROM image_table")
    fun getAllImages(): LiveData<List<ImageDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageDetail)

    @Update
    suspend fun update(image: ImageDetail)

    @Delete
    suspend fun delete(image: ImageDetail)


}