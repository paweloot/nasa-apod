package com.paweloot.nasaapod.network

import com.paweloot.nasaapod.data.model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApodApi {

    @GET("planetary/apod")
    fun getPhotoForDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Single<Photo>
}