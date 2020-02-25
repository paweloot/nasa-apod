package com.paweloot.nasaapod.data.model

import com.google.gson.annotations.SerializedName

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
}