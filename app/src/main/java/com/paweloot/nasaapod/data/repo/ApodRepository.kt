package com.paweloot.nasaapod.data.repo

import com.paweloot.nasaapod.BuildConfig
import com.paweloot.nasaapod.data.model.Apod
import com.paweloot.nasaapod.network.NasaApodApi
import com.paweloot.nasaapod.util.DateUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApodRepository(private val nasaApodApi: NasaApodApi) {

    fun getPhotoForDate(date: String): Single<Apod> =
        nasaApodApi.getPhotoForDate(date, BuildConfig.NASA_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getLastWeekPhotos(): Single<List<Apod>> {
        val lastWeekDates = DateUtils.buildLastWeekDates()
        val singles = mutableListOf<Single<Apod>>()

        lastWeekDates.forEach { date ->
            singles.add(getPhotoForDate(date))
        }

        return Single.merge(singles)
            .toObservable()
            .toList()
    }
}