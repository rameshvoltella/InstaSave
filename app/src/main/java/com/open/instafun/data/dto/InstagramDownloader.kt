package com.open.instafun.data.dto

import com.squareup.moshi.Json

data class InstagramDownloader(
    @Json(name="channelName")
    val channelName: String,
    @Json(name="channelThumbnail")
    val channelThumbnail: String,
    @Json(name="downloadUrl")
    val downloadUrl: String,
    @Json(name="videoThumbnail")
    val videoThumbnail: String,
    @Json(name="videoTitle")
    val videoTitle: String
)