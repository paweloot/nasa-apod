package com.paweloot.nasaapod.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val date: String = "",
    val title: String = "",
    val explanation: String = "",
    @SerializedName("media_type") val mediaType: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("hdurl") val hdUrl: String = ""
)