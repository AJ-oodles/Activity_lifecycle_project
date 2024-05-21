package com.example.activiy_lifecycle_project

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStartStop: Button

    private var seconds = 0
    private var running = false
    private var wasRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStartStop = findViewById(R.id.btnStartStop)

        btnStartStop.setOnClickListener {
            if (running) {
                stopTimer()
            } else {
                startTimer()
            }
        }

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }

        runTimer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    private fun startTimer() {
        running = true
        btnStartStop.text = "Stop"
    }

    private fun stopTimer() {
        running = false
        btnStartStop.text = "Start"
    }

    private fun runTimer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60

                val time = String.format("%d:%02d:%02d", hours, minutes, secs)
                tvTimer.text = time

                if (running) {
                    seconds++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }
}
