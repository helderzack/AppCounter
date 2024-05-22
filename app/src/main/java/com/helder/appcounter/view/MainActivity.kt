package com.helder.appcounter.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.helder.appcounter.ui.theme.AppCounterTheme
import com.helder.appcounter.viewmodel.MainViewModel
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainViewModel = viewModel()
            AppCounterTheme {
                AppCounter(
                    mainViewModel = mainViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AppCounter(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        val context = LocalContext.current
//        val time = mainViewModel.time
        val digit = mainViewModel.digit

        Text(text = "Timer")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(
//                text = "Timer: ${time.format(DateTimeFormatter.ofPattern(" HH:mm:ss "))}",
//                fontSize = 32.sp
//            )
            Text(
                text = "Digit: ${String(digit)}",
                fontSize = 32.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 1..3) {
                    Button(onClick = {
                        mainViewModel.updateDigit(i)
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
                        mainViewModel.updateDigit(i)
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
                        mainViewModel.updateDigit(i)
                    }) {
                        Text(text = "$i")
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Button(onClick = {
                    mainViewModel.updateDigit(0)
                }) {
                    Text(text = "0")
                }
                IconButton(
                    onClick = {
                        mainViewModel.clearCounter()
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
                    mainViewModel.startCountdown {
                        Toast.makeText(context, "Timer was completed!", Toast.LENGTH_SHORT).show()
                    }
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
//        AppCounter(modifier = Modifier.fillMaxSize())
    }
}