package com.example.imgvu.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imgvu.api.UnsplashApi
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.db.ImgVuDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImgRepository @Inject constructor(
    private val imgVuDao: ImgVuDao,
    private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
           config = PagingConfig(
               pageSize = 20,
               maxSize = 100,
               enablePlaceholders = false
           ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query)}
        ).liveData

    suspend fun insert(image: ImageDetail) = imgVuDao.insert(image)

    suspend fun update(image: ImageDetail) = imgVuDao.update(image)

    suspend fun delete(image: ImageDetail) = imgVuDao.delete(image)

    fun getAllImages() = imgVuDao.getAllImages()
}