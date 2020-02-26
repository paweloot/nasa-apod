package com.paweloot.nasaapod.ui

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections

class NavManager {

    val newDestination = MutableLiveData<NavDirections>()

    fun navigateTo(destination: NavDirections) {
        newDestination.value = destination
    }
}