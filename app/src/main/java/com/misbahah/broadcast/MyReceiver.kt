package com.misbahah.broadcast

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.misbahah.utilities.LAST_RUN
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
import com.misbahah.utilities.makeStatusNotification
import timber.log.Timber

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val settings = context.applicationContext.getSharedPreferences(MAIN_ACTIVITY_PREFS, Service.MODE_PRIVATE)

        // Are notifications enabled?
        if (settings.getBoolean(ENABLED, true)) {
            // Is it time for a notification?
            val channel = createChannel()
            makeStatusNotification("لا تنس ذكر الله", context, channel)
            Timber.i("onStartCommand: %s", settings.getLong(LAST_RUN, -1))
            setAlarm(context)
        } else {
            Timber.i("Notifications are disabled")
        }
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

    companion object {
        fun setAlarm(context: Context) {
            val broadcastIntent = Intent(context, MyReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context.applicationContext, 121314, broadcastIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)
            Timber.i("Alarm set")
        }

        const val TAG = "AlarmReceiver"

        private const val MILLISECS_PER_MIN = 10000L
        const val delay = MILLISECS_PER_MIN * 1// 0.5 minutes (for testing)
        //private const val MILLISECS_PER_DAY = 86400000L
        //private const val delay = MILLISECS_PER_DAY * 3 // 3 days

        const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
                "Check Last Run"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
                "Shows notifications if the user didn't open the app for 3 days"
        const val NOTIFICATION_TITLE = "ألا بذكر الله تطمئن القلوب"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1021
        const val ENABLED = "is_enabled"
    }
}