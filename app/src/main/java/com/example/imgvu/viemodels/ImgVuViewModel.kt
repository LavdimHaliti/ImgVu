package com.example.imgvu.viemodels

import android.app.DownloadManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.repository.ImgRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImgVuViewModel @Inject constructor(
    private val repository: ImgRepository,
    state: SavedStateHandle
) : ViewModel() {

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "nature"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentQuery.value = query
    }

    fun insertImage(image: ImageDetail) = viewModelScope.launch {
        repository.insert(image)
    }

    fun updateImage(image: ImageDetail) = viewModelScope.launch {
        repository.update(image)
    }

    fun deleteImage(image: ImageDetail) = viewModelScope.launch {
        repository.delete(image)
    }

    fun getAllImages() = repository.getAllImages()
}