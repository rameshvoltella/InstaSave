package com.open.instafun.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instagram_downloader")
data class InstagramDBDownloader(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val channelName: String,
    val channelThumbnail: String,
    val downloadUrl: String,
    val videoThumbnail: String,
    val videoTitle: String,
    val reelsUrl: String // New field added
)
