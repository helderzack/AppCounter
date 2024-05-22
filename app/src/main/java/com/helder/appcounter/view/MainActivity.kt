package com.helder.appcounter.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.helder.appcounter.R
import com.helder.appcounter.ui.theme.AppCounterTheme
import com.helder.appcounter.viewmodel.MainViewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContent {
            mainViewModel = viewModel()
            AppCounterTheme {
                AppCounter(
                    mainViewModel = mainViewModel,
                    createNotification = { createNotification() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun createNotificationChannel() {
        val channelId = getString(R.string.channel_id)
        val channelName = getString(R.string.channel_name)
        val channelDescription = getString(R.string.channel_description)
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification() {
        Log.d("NOTIFICATION_TEST", "CREATED")
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val NOTIFICATION_ID = Random.nextInt()

        val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Countdown Timer Title")
            .setContentText("Notification text").setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if(ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                return@with
            }

            notify(NOTIFICATION_ID, builder.build())
        }
    }
}

@Composable
fun AppCounter(
    mainViewModel: MainViewModel,
    createNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    mainViewModel.startCountdown(createNotification = createNotification, onFinishedTimer = {
                        Looper.prepare()
                        Toast.makeText(context, "Timer was completed!", Toast.LENGTH_SHORT).show()
                    })
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