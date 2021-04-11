package com.misbahah.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.misbahah.R
import com.misbahah.broadcast.MyReceiver.Companion.CHANNEL_ID
import com.misbahah.broadcast.MyReceiver.Companion.NOTIFICATION_ID
import com.misbahah.broadcast.MyReceiver.Companion.NOTIFICATION_TITLE
import java.lang.NullPointerException

fun makeStatusNotification(message: String, context: Context, channel: NotificationChannel?) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Add the channel
        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (channel != null)
            notificationManager.createNotificationChannel(channel)
        else
            throw NullPointerException("Notification Channel can't be NULL")
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_tasbih)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(1))
            .setCategory(NotificationCompat.CATEGORY_ALARM)

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}