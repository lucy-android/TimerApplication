package com.example.studies.timerapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studies.timerapplication.util.event.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val DEFAULT_TIME_ElAPSED = 2000L
    }

    private var job: Job? = null

    private val _showToastEventFlow = SingleLiveEvent<Unit>()
    val showToastEventFlow: LiveData<Unit> = _showToastEventFlow

    fun cancelTimer() {
        job?.cancel()
    }

    fun scheduleNewTimer() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(DEFAULT_TIME_ElAPSED)
            _showToastEventFlow.value = Unit
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}