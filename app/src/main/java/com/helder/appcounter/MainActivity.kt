package com.helder.appcounter

import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import java.util.Timer
import java.util.TimerTask

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
        var time by rememberSaveable {
            mutableStateOf("000000")
        }

        var timeIndex by rememberSaveable {
            mutableIntStateOf(5)
        }

        var onChangedTime by rememberSaveable {
            mutableStateOf("000000")
        }

        Text(text = "Timer")
        Text(text = "On Changed Time: $onChangedTime")
        Text(text = "Current index: $timeIndex")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${time[0]}${time[1]}h ${time[2]}${time[3]}m ${time[4]}${time[5]}s", fontSize = 30.sp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 1..3) {
                    Button(onClick = {
                        if(!checkIfFull(timeIndex)) {
                            val sb = StringBuilder(onChangedTime)
                            sb.setCharAt(timeIndex, '0' + i)
                            onChangedTime = sb.toString()
                            time = onChangedTime
                            timeIndex--
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
                        if(!checkIfFull(timeIndex)) {
                            val sb = StringBuilder(onChangedTime)
                            sb.setCharAt(timeIndex, '0' + i)
                            onChangedTime = sb.toString()
                            time = onChangedTime
                            timeIndex--
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
                        if(!checkIfFull(timeIndex)) {
                            val sb = StringBuilder(onChangedTime)
                            sb.setCharAt(timeIndex, '0' + i)
                            onChangedTime = sb.toString()
                            time = onChangedTime
                            timeIndex--
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
                    if(!checkIfFull(timeIndex) && timeIndex != 5) {
                        val sb = StringBuilder(onChangedTime)
                        sb.setCharAt(timeIndex, '0' + 0)
                        onChangedTime = sb.toString()
                        time = onChangedTime
                        timeIndex--
                    }

                }) {
                    Text(text = "0")
                }
                IconButton(
                    onClick = {
                        time = "00h 00m 00s"
                        onChangedTime = "000000"
                        timeIndex = 5
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Timer")
                }
            }
        }
    }
}

//private fun changeString() {
//    if(!checkIfFull(timeIndex) && timeIndex != 0) {
//        val sb = StringBuilder(onChangedTime)
//        sb.setCharAt(timeIndex, '0' + 0)
//        onChangedTime = sb.toString()
//        time = onChangedTime
//        timeIndex--
//    }
//}

private fun checkIfFull(index: Int): Boolean {
    return index <= -1
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppCounterTheme {
        AppCounter(modifier = Modifier.fillMaxSize())
    }
}