package org.azkary.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.azkary.main.ui.MainActivity
import org.azkary.utilities.DataStoreManager
import org.azkary.utilities.makeStatusNotification
import java.util.concurrent.TimeUnit

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataStoreManager: DataStoreManager
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val isFirstRun = dataStoreManager.isFirstRun()
        if (isFirstRun) {
                dataStoreManager.setIsFirstRun(false)
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(appContext, 0, intent, 0)
            makeStatusNotification("لا تنس ذكر الله", appContext, createChannel(), pendingIntent)
        }

        return Result.success()
    }

    private fun createChannel(): NotificationChannel? {
        var channel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
        }
        return channel
    }

    companion object {

        fun startNotificationWorker(
            context: Context,
            dataStoreManager: DataStoreManager
        ) {
            val appContext = context.applicationContext

            runBlocking {
                dataStoreManager
                    .setIsFirstRun(true)
            }

            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(3, TimeUnit.DAYS)
                .build()

            val workManager = WorkManager.getInstance(appContext)

            workManager.enqueueUniquePeriodicWork(
                "unique",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }

        const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
            "Check Last Run"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications if the user didn't open the app for 3 days"
        const val NOTIFICATION_TITLE = "ألا بذكر الله تطمئن القلوب"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1021
    }

}