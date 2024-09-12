package com.open.instafun.data.remote.api


import com.open.instafun.data.dto.InstagramDownloader
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiServices {


    @GET
    suspend fun getVideoLink(
        @Url url: String,
        @Header("x-apihub-key") key: String = "iD8GmEzCAPZM6byGbiw7hJ70nrbMXKknIAnzAwVc7a3UWlwL-8",
        @Header("x-apihub-host") host:String="Video-Downloader.allthingsdev.co",
        @Header("x-apihub-endpoint") enp:String="6bd5f6f8-78ac-4ab5-8c1c-f41614768299"

    ) : Response<InstagramDownloader>

}