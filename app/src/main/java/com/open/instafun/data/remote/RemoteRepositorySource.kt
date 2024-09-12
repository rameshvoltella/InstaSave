package com.open.instafun.data.remote

import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import kotlinx.coroutines.flow.Flow

interface RemoteRepositorySource {

    suspend fun getVideoLink(url:String) : Flow<Resource<InstagramDownloader>>


}