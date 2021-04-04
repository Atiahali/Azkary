package com.misbahah.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.misbahah.utilities.LAST_RUN
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
import com.misbahah.utilities.makeStatusNotification


class CheckRecentRun : Service() {
    override fun onCreate() {
        Log.i(TAG, "Service started")
        val settings = applicationContext.getSharedPreferences(MAIN_ACTIVITY_PREFS, MODE_PRIVATE)

        // Are notifications enabled?
        if (settings.getBoolean(ENABLED, true)) {
            // Is it time for a notification?
            if (settings.getLong(LAST_RUN, Long.MAX_VALUE) < System.currentTimeMillis() - delay) {

                val channel = createChannel()

                makeStatusNotification("لا تنس ذكر الله", this.applicationContext, channel)
            }
        } else {
            Log.i(TAG, "Notifications are disabled")
        }

        // Set an alarm for the next time this service should run:
        setAlarm()

        Log.i(TAG, "Service stopped")
        stopSelf()
    }

    private fun createChannel(): NotificationChannel? {
        var channel: NotificationChannel? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
        }
        return channel
    }

    private fun setAlarm() {
        val serviceIntent = Intent(this, CheckRecentRun::class.java)
        val pendingIntent = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)
        Log.i(TAG, "Alarm set")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val TAG = "CheckRecentPlay"
        private const val MILLISECS_PER_DAY = 86400000L
        // private const val MILLISECS_PER_MIN = 30000L
        // const val delay = MILLISECS_PER_MIN * 1// 0.5 minutes (for testing)
        private const val delay = MILLISECS_PER_DAY * 3 // 3 days

        const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
                "Check last run"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
                "Shows notifications if the user didn't open the app for 3 years"
        const val NOTIFICATION_TITLE = "ألا بذكر الله تطمئن القلوب"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1001
        const val ENABLED = "is_enabled"
    }
}