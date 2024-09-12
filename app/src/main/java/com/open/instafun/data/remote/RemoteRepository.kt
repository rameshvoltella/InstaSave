package com.open.instafun.data.remote

import com.open.instafun.data.Resource
import com.open.instafun.data.dto.InstagramDownloader

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepository@Inject constructor(
    private val remoteRepository: RemoteData,
) : RemoteRepositorySource {
    override suspend fun getVideoLink(url: String): Flow<Resource<InstagramDownloader>> {

        return flow { emit(remoteRepository.getVideoLink(url)) }

    }


}

