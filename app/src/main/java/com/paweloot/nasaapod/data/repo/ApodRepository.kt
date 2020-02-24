package com.paweloot.nasaapod.data.repo

import com.paweloot.nasaapod.BuildConfig
import com.paweloot.nasaapod.network.NasaApodApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApodRepository(private val nasaApodApi: NasaApodApi) {

    fun getPhotoForDate(date: String) =
        nasaApodApi.getPhotoForDate(date, BuildConfig.NASA_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}