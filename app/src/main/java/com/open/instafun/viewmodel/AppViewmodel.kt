package com.open.instafun.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import com.open.instafun.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AppViewmodel @Inject constructor(val remoteRepository: RemoteRepository) : ViewModel()  {
    val getVideoLinkPrivate =MutableLiveData<Resource<InstagramDownloader>>()
    val getVideoLink :LiveData<Resource<InstagramDownloader>>get()=getVideoLinkPrivate




    fun getVideo(url:String)
    {
        viewModelScope.launch {
            remoteRepository.getVideoLink(url).collect{
                getVideoLinkPrivate.value=it
            }
        }
    }


}