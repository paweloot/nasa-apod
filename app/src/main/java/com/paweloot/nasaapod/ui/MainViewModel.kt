package com.paweloot.nasaapod.ui

import androidx.lifecycle.MutableLiveData
import com.paweloot.nasaapod.data.model.Apod
import com.paweloot.nasaapod.data.repo.ApodRepository
import io.reactivex.rxkotlin.subscribeBy

class MainViewModel(private val apodRepository: ApodRepository) : DisposingViewModel() {

    data class ViewState(
        val data: List<Apod> = listOf(),
        val isLoading: Boolean = true,
        val errorMsg: String = ""
    )

    val state = MutableLiveData<ViewState>().also { it.value = ViewState() }

    init {
        getPhotoList()
    }

    private fun getPhotoList() {
        addDisposable(
            apodRepository.getLastWeekPhotos()
                .subscribeBy(
                    onSuccess = this::onPhotosRetrieved,
                    onError = this::onError
                )
        )
    }

    private fun onPhotosRetrieved(data: List<Apod>) {
        state.value = state.value?.copy(
            isLoading = false,
            data = data.sortedBy { it.date }.asReversed()
        )
    }

    private fun onError(throwable: Throwable) {
        state.value = state.value?.copy(
            isLoading = false,
            errorMsg = throwable.localizedMessage ?: ""
        )
    }
}