package com.example.studies.timerapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studies.timerapplication.util.event.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {

    companion object {
        const val DEFAULT_TIME_ElAPSED = 10000L
    }

    private var task: TimerTask? = null
    private var timer: Timer? = null

    private val _showToastEventFlow = SingleLiveEvent<Unit>()
    val showToastEventFlow: LiveData<Unit> = _showToastEventFlow

    fun cancelTimer() {
        task?.cancel()
        timer?.cancel()
    }

    fun scheduleNewTimer() {
        task = object : TimerTask() {
            override fun run() {
                _showToastEventFlow.postValue(Unit)
            }
        }
        timer = Timer("Timer")
        timer?.schedule(task, DEFAULT_TIME_ElAPSED)
    }

    override fun onCleared() {
        cancelTimer()
        super.onCleared()
    }
}