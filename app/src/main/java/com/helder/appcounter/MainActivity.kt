package com.helder.appcounter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helder.appcounter.ui.theme.AppCounterTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCounterTheme {
                AppCounter(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AppCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        var time by rememberSaveable {
            mutableStateOf(LocalTime.parse("00:00:00", formatter))
        }

        var digit by rememberSaveable {
            mutableStateOf(time.format(formatter).toString().toCharArray())
        }
        var digitIndex by rememberSaveable {
            mutableIntStateOf(digit.size - 1)
        }
        Text(String(digit))
        Text(text = "Timer")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = time.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                fontSize = 32.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 1..3) {
                    Button(onClick = {
                        if (digitIndex >= 0 && digit[digitIndex] != ':') {
                            digit[digitIndex] = i.digitToChar()
                            Log.d("DIGIT", digitIndex.toString())
                            digit = digit.copyOf()
                        }
                        digitIndex--
                        when (digitIndex) {
                            2 -> digitIndex--
                            5 -> digitIndex--
                        }
                    }) {
                        Text(text = "$i")
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 4..6) {
                    Button(onClick = {
                        if (digitIndex >= 0 && digit[digitIndex] != ':') {
                            digit[digitIndex] = i.digitToChar()
                            Log.d("DIGIT", digitIndex.toString())
                            digit = digit.copyOf()
                        }
                        digitIndex--
                        when (digitIndex) {
                            2 -> digitIndex--
                            5 -> digitIndex--
                        }
                    }) {
                        Text(text = "$i")
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 7..9) {
                    Button(onClick = {
                        if (digitIndex >= 0 && digit[digitIndex] != ':') {
                            digit[digitIndex] = i.digitToChar()
                            Log.d("DIGIT", digitIndex.toString())
                            digit = digit.copyOf()
                        }
                        digitIndex--
                        when (digitIndex) {
                            2 -> digitIndex--
                            5 -> digitIndex--
                        }
                    }) {
                        Text(text = "$i")
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Button(onClick = {
                    if (digitIndex < digit.size - 1 && digitIndex >= 0 && digit[digitIndex] != ':') {
                        digit[digitIndex--] = 0.digitToChar()
                        Log.d("DIGIT", digitIndex.toString())
                        digit = digit.copyOf()
                    }
                    digitIndex--
                    when (digitIndex) {
                        2 -> digitIndex--
                        5 -> digitIndex--
                    }
                }) {
                    Text(text = "0")
                }
                IconButton(
                    onClick = {
                        time = LocalTime.parse("00:00:00", formatter)
                        digit = time.format(formatter).toString().toCharArray()
                        digitIndex = digit.size - 1
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Timer")
                }
            }
            IconButton(
                onClick = {
                    val timeString = digit.joinToString("")
                    val hours = timeString.substring(0,2).toInt()
                    val minutes = timeString.substring(3,5).toInt()
                    val seconds = timeString.substring(6).toInt()
                    Log.d("TIME", ": $hours -> $minutes -> $seconds")

                    val totalSeconds = (hours * 3600) + (minutes * 60) + seconds
                    val normalizedHours = (totalSeconds / 3600) % 24
                    val remainingSeconds = totalSeconds % 3600
                    val normalizedMinutes = remainingSeconds / 60
                    val normalizedSeconds = remainingSeconds % 60

                    time = LocalTime.of(normalizedHours, normalizedMinutes, normalizedSeconds)

//                    val timer = fixedRateTimer(initialDelay = 0, period = 1000) {
//                        this.cancel()
//                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Clear Timer")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppCounterTheme {
        AppCounter(modifier = Modifier.fillMaxSize())
    }
}