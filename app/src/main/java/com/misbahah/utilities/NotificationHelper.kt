package com.misbahah.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.misbahah.R
import com.misbahah.worker.NotificationWorker.Companion.CHANNEL_ID
import com.misbahah.worker.NotificationWorker.Companion.NOTIFICATION_ID
import com.misbahah.worker.NotificationWorker.Companion.NOTIFICATION_TITLE

fun makeStatusNotification(message: String, context: Context, channel: NotificationChannel?, pendingIntent: PendingIntent?) {

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
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setVibrate(LongArray(1))
        .setCategory(NotificationCompat.CATEGORY_ALARM)

    if(pendingIntent != null) {
        builder.setContentIntent(pendingIntent)
    }

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}