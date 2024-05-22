package com.helder.appcounter.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.fixedRateTimer

class MainViewModel(
): ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    private var time by mutableStateOf(LocalTime.parse("00:00:00", formatter))
    var digit by mutableStateOf(time.format(formatter).toString().toCharArray())
        private set
    private var digitIndex by mutableIntStateOf(digit.size - 1)

    fun startCountdown(onFinishedTimer: () -> Unit,) {
        val timeString = digit.joinToString("")
        val hours = timeString.substring(0, 2).toInt()
        val minutes = timeString.substring(3, 5).toInt()
        val seconds = timeString.substring(6).toInt()
        Log.d("TIME", ": $hours -> $minutes -> $seconds")

        val totalSeconds = (hours * 3600) + (minutes * 60) + seconds
        val normalizedHours = (totalSeconds / 3600) % 24
        val remainingSeconds = totalSeconds % 3600
        val normalizedMinutes = remainingSeconds / 60
        val normalizedSeconds = remainingSeconds % 60

        time = LocalTime.of(normalizedHours, normalizedMinutes, normalizedSeconds)

        fixedRateTimer(initialDelay = 0, period = 1000) {
            time = time.minusSeconds(1)
            digit = time.format(formatter).toString().toCharArray()
            if(time.equals(LocalTime.parse("00:00:00", formatter))) {
                onFinishedTimer()
                this.cancel()
            }
        }
    }
    fun updateDigit(value: Int) {
        if(value == 0 && digitIndex >= digit.size - 1) {
            return
        }
        if (digitIndex >= 0 && digit[digitIndex] != ':') {
            digit[digitIndex] = value.digitToChar()
            Log.d("DIGIT", digitIndex.toString())
            digit = digit.copyOf()
        }
        digitIndex--
        when (digitIndex) {
            2 -> digitIndex--
            5 -> digitIndex--
        }
    }

    fun clearCounter() {
        time = LocalTime.parse("00:00:00", formatter)
        digit = time.format(formatter).toString().toCharArray()
        digitIndex = digit.size - 1
    }

}