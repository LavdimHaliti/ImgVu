package com.example.imgvu.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imgvu.api.UnsplashApi
import com.example.imgvu.data.ImageDetail
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, ImageDetail>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDetail> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position -1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageDetail>): Int? {
        return state.anchorPosition
    }


}