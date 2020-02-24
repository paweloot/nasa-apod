package com.paweloot.nasaapod.di

import com.paweloot.nasaapod.data.repo.ApodRepository
import org.koin.dsl.module

val appModule = module {
    single { ApodRepository(get()) }
}