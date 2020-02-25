package com.paweloot.nasaapod.data.model

import com.google.gson.annotations.SerializedName
import com.paweloot.nasaapod.util.YouTubeUtils

data class Apod(
    val date: String = "",
    val title: String = "",
    val explanation: String = "",
    @SerializedName("media_type") val mediaType: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("hdurl") val hdUrl: String = ""
) {

    val apodType: ApodType
        get() = when (mediaType) {
            "image" -> ApodType.IMAGE
            else -> ApodType.VIDEO
        }

    val thumbnailUrl: String
        get() = when (apodType) {
            ApodType.IMAGE -> url
            ApodType.VIDEO -> YouTubeUtils.buildVideoThumbnailUrl(url)
        }
}