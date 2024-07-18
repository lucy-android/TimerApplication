package com.example.studies.timerapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.studies.timerapplication.util.event.SingleLiveEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.Volatile


class MainViewModel : ViewModel() {

    companion object {
        const val DEFAULT_TIME_ElAPSED = 20_000L
    }

    private var task: Runnable? = null

    @Volatile
    private var lastClickedTime: Long = System.currentTimeMillis()

    private val _showToastEventFlow = SingleLiveEvent<Unit>()
    val showToastEventFlow: LiveData<Unit> = _showToastEventFlow

    private var executorService: ExecutorService = ThreadPoolExecutor(
        1, 1, 0L, TimeUnit.MILLISECONDS,
        LinkedBlockingQueue()
    )

    fun cancelTimer() {
        task = null
    }

    fun scheduleNewTimer() {

        task = Runnable {
            Log.d("APP_TAG", "current thread name: ${Thread.currentThread().name}")
            Thread.sleep(DEFAULT_TIME_ElAPSED)
            if (System.currentTimeMillis() - lastClickedTime > DEFAULT_TIME_ElAPSED) {
                _showToastEventFlow.postValue(Unit)
            }
        }
        executorService.execute(task)
        lastClickedTime = System.currentTimeMillis()
    }

    override fun onCleared() {
        task = null
        executorService.shutdown()
        super.onCleared()
    }
}