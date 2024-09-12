package com.open.instafun.data.remote

import com.open.instafun.data.NetworkConnectivity
import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import com.open.instafun.data.remote.RemoteDataSource
import com.open.instafun.data.remote.api.ServiceGenerator
import com.open.instafun.errors.NETWORK_ERROR
import com.open.instafun.errors.NO_INTERNET_CONNECTION
import com.open.instafun.errors.SERVER_ERROR

import retrofit2.Response
import java.io.IOException

import javax.inject.Inject

class RemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
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
        TODO("Not yet implemented")
    }


}