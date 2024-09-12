package com.open.instafun.data.remote

import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader


interface RemoteDataSource {

    suspend fun getVideoLink(url:String) : Resource<InstagramDownloader>

}