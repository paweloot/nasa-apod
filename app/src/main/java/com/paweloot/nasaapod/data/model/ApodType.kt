package com.paweloot.nasaapod.data.model

sealed class ApodType {
    object IMAGE : ApodType()
    object VIDEO : ApodType()
}