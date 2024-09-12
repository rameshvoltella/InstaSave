package com.open.instafun.database

import androidx.room.*

@Dao
interface InstagramDownloaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(download: InstagramDBDownloader)

    @Query("SELECT * FROM instagram_downloader WHERE reelsUrl = :reelsUrl LIMIT 1")
    suspend fun getDownloaderByReelsUrl(reelsUrl: String): InstagramDBDownloader?

    suspend fun insertIfNotExists(download: InstagramDBDownloader) {
        val existingDownload = getDownloaderByReelsUrl(download.reelsUrl)
        if (existingDownload == null) {
            insert(download)
        }
    }

    @Query("SELECT * FROM instagram_downloader")
    suspend fun getAllDownloads(): List<InstagramDBDownloader>

    @Delete
    suspend fun delete(download: InstagramDBDownloader)

    @Query("DELETE FROM instagram_downloader WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM instagram_downloader")
    suspend fun clearAll()
}
