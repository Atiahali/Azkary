package com.misbahah.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.misbahah.main.ui.MainActivity
import com.misbahah.utilities.FIRST_RUN_KEY
import com.misbahah.utilities.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sharedPreferences: SharedPreferences
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        val isFirstRun = sharedPreferences.getInt(FIRST_RUN_KEY, NOT_FIRST_RUN) == FIRST_RUN

        if (isFirstRun) {
            sharedPreferences.edit {
                putInt(FIRST_RUN_KEY, NOT_FIRST_RUN)
            }
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
            sharedPreferences: SharedPreferences,
            lifecycleOwner: LifecycleOwner
        ) {
            val appContext = context.applicationContext
            sharedPreferences.edit {
                putInt(FIRST_RUN_KEY, FIRST_RUN)
            }

            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(3, TimeUnit.DAYS)
                .build()

            val workManager = WorkManager.getInstance(appContext)

            workManager.enqueueUniquePeriodicWork(
                "unique",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )

            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(lifecycleOwner, {
                if (it.state == WorkInfo.State.RUNNING)
                    Timber.i(
                        "itw  startNotificationWorker %s",
                        sharedPreferences
                            .getInt(
                                FIRST_RUN_KEY,
                                -1
                            )
                    )
            })
        }

        const val NOT_FIRST_RUN = 1
        const val FIRST_RUN = 0
        const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
            "Check Last Run"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications if the user didn't open the app for 3 days"
        const val NOTIFICATION_TITLE = "ألا بذكر الله تطمئن القلوب"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1021
    }

}