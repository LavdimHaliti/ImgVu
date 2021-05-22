package com.example.imgvu.api

import androidx.paging.PagingData
import com.example.imgvu.data.ImageDetail

data class UnsplashResponse(
    val results: List<ImageDetail>
)