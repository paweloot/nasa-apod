package com.paweloot.nasaapod.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.paweloot.nasaapod.network.NasaApodApi
import com.paweloot.nasaapod.util.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    factory { provideNasaApodApi(get()) }
}

private fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

private fun provideNasaApodApi(retrofit: Retrofit): NasaApodApi =
    retrofit.create(NasaApodApi::class.java)