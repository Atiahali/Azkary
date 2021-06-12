package com.misbahah.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.work.*
import com.misbahah.utilities.FIRST_RUN_KEY
import com.misbahah.utilities.WORKER_PREFERECES
import com.misbahah.utilities.makeStatusNotification
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NotificationWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {

        val sharedPreferences = this.applicationContext
            .getSharedPreferences(WORKER_PREFERECES, Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getInt(FIRST_RUN_KEY, NOT_FIRST_RUN) == FIRST_RUN

        Timber.i("itw doWork $isFirstRun")

        if (isFirstRun) {
            sharedPreferences.edit {
                putInt(FIRST_RUN_KEY, NOT_FIRST_RUN)
            }
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            makeStatusNotification("لا تنس ذكر الله", context, createChannel(), pendingIntent)
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

        fun startNotificationWorker(_context: Context, appCompatActivity: AppCompatActivity) {
            val context = _context.applicationContext
            context.getSharedPreferences(WORKER_PREFERECES, AppCompatActivity.MODE_PRIVATE).edit {
                putInt(FIRST_RUN_KEY, FIRST_RUN)
            }

            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(16, TimeUnit.MINUTES)
                .build()

            val workManager = WorkManager.getInstance(context)

            workManager.enqueueUniquePeriodicWork(
                "unique",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )

            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(appCompatActivity, {
                if (it.state == WorkInfo.State.RUNNING)
                    Timber.i(
                        "itw  startNotificationWorker %s",
                        context.getSharedPreferences(WORKER_PREFERECES, Context.MODE_PRIVATE)
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