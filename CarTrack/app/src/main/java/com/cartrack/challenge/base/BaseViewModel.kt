package com.cartrack.challenge.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartrack.challenge.models.CarTrackError
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<CarTrackError>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}