package com.paweloot.nasaapod.di

import com.paweloot.nasaapod.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
}