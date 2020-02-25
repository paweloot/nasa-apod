package com.paweloot.nasaapod.data.repo

import com.paweloot.nasaapod.BuildConfig
import com.paweloot.nasaapod.data.model.Photo
import com.paweloot.nasaapod.network.NasaApodApi
import com.paweloot.nasaapod.util.DateUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApodRepository(private val nasaApodApi: NasaApodApi) {

    fun getPhotoForDate(date: String): Single<Photo> =
        nasaApodApi.getPhotoForDate(date, BuildConfig.NASA_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getLastWeekPhotos(): Single<List<Photo>> {
        val lastWeekDates = DateUtils.buildLastWeekDates()
        val singles = mutableListOf<Single<Photo>>()

        lastWeekDates.forEach { date ->
            singles.add(getPhotoForDate(date))
        }

        return Single.merge(singles)
            .toObservable()
            .toList()
    }
}