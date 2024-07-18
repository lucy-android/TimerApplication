package com.example.studies.timerapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.studies.timerapplication.MainViewModel.Companion.DEFAULT_TIME_ElAPSED


class MainActivity : AppCompatActivity() {

    private var viewmodel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]

        initObservers()

        button.setOnClickListener {
            viewmodel?.cancelTimer()
            viewmodel?.scheduleNewTimer()
        }
    }

    private fun initObservers() {
        viewmodel?.showToastEventFlow?.observe(this) {
            Toast.makeText(
                this,
                "Clicking stopped ${((DEFAULT_TIME_ElAPSED / 1000).toDouble()).toInt()} seconds ago",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}