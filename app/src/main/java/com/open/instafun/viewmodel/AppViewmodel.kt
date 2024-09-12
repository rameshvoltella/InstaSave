package com.open.instafun.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import com.open.instafun.data.remote.RemoteRepository
import com.open.instafun.database.InstagramDBDownloader
import com.open.instafun.database.InstagramRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AppViewmodel @Inject constructor(val remoteRepository: RemoteRepository,private val repository: InstagramRepository) : ViewModel()  {
    val getVideoLinkPrivate =MutableLiveData<Resource<InstagramDownloader>>()
    val getVideoLink :LiveData<Resource<InstagramDownloader>>get()=getVideoLinkPrivate

    // Store the index of the currently visible page
    var currentPageIndex by mutableStateOf(0)
        private set
    fun setCurrentPage(index: Int) {
        currentPageIndex = index
    }

    fun getVideo(url:String)
    {
        viewModelScope.launch {
            remoteRepository.getVideoLink(url).collect{
                getVideoLinkPrivate.value=it
            }
        }
    }

    var downloads = mutableStateListOf<InstagramDBDownloader>()
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    fun refreshData() {
        viewModelScope.launch {
            isRefreshing = true
            downloads.clear()
            downloads.addAll(repository.getAllDownloads())
            isRefreshing = false
        }
    }


}