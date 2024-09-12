package com.open.instafun.data.remote

import android.util.Log
import com.open.instafun.data.NetworkConnectivity
import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import com.open.instafun.data.remote.RemoteDataSource
import com.open.instafun.data.remote.api.ApiServices
import com.open.instafun.data.remote.api.ServiceGenerator
import com.open.instafun.database.InstagramDBDownloader
import com.open.instafun.database.InstagramRepository
import com.open.instafun.errors.NETWORK_ERROR
import com.open.instafun.errors.NO_INTERNET_CONNECTION
import com.open.instafun.errors.SERVER_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import retrofit2.Response
import java.io.IOException

import javax.inject.Inject

class RemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
    private val instagramDBRepository: InstagramRepository
) : RemoteDataSource {


    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        try {
            if (!networkConnectivity.isConnected()) {
                return NO_INTERNET_CONNECTION
            }
            return try {
                val response = responseCall.invoke()
                val responseCode = response.code()
                if (response.isSuccessful) {

                    response.body()
                } else {
                    responseCode
                }
            } catch (e: IOException) {
                e.printStackTrace()
                NETWORK_ERROR
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return SERVER_ERROR
        }
    }

    override suspend fun getVideoLink(url: String): Resource<InstagramDownloader> {
        val apiService = serviceGenerator.createService(ApiServices::class.java)
        if(instagramDBRepository.isReelsUrlPresent(url))
        {
            return Resource.DataError(300)

        }else {
            val fullUrl = "$url&igsh=MzRlODBiNWFlZA=="

            return when (val response = processCall {
                apiService.getVideoLink(
                    "https://Video-Downloader.proxy-production.allthingsdev.co/instagram/download?url=$fullUrl"
                )
            }) {
                is Any -> {
                    try {
                        (response is InstagramDownloader).let {
                            val responseIncomming = response as InstagramDownloader
                            val dbDataValue: InstagramDBDownloader = InstagramDBDownloader(
                                channelName = responseIncomming.channelName,
                                channelThumbnail = responseIncomming.channelThumbnail,
                                videoThumbnail = responseIncomming.videoThumbnail,
                                videoTitle = responseIncomming.videoTitle,
                                downloadUrl = responseIncomming.downloadUrl,
                                reelsUrl = url
                            )
                            instagramDBRepository.insertIfNotExists(dbDataValue)

                            Resource.Success(response as InstagramDownloader)


                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                        Resource.DataError(404)
                    }
                }

                else -> {
                    Resource.DataError(errorCode = response as Int)
                }
            }
        }
    }


}