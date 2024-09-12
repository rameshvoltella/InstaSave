package com.open.instafun.database

import javax.inject.Inject

class InstagramRepository @Inject constructor(
    private val dao: InstagramDownloaderDao
) {

    suspend fun insertIfNotExists(download: InstagramDBDownloader) {
        dao.insertIfNotExists(download)
    }

    suspend fun getAllDownloads(): List<InstagramDBDownloader> {
        return dao.getAllDownloads()
    }

    suspend fun delete(download: InstagramDBDownloader) {
        dao.delete(download)
    }

    suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }

    suspend fun clearAll() {
        dao.clearAll()
    }

    // Check if a reel URL is already present in the database
    suspend fun isReelsUrlPresent(reelsUrl: String): Boolean {
        return dao.getDownloaderByReelsUrl(reelsUrl) != null
    }
}
