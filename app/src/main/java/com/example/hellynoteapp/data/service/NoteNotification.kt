package com.example.hellynoteapp.data.service

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.hellynoteapp.R
import com.example.hellynoteapp.utils.Constance.channelID
import com.example.hellynoteapp.utils.Constance.messageExtra
import com.example.hellynoteapp.utils.Constance.notificationID
import com.example.hellynoteapp.utils.Constance.titleExtra

class NoteNotification:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = NotificationCompat.Builder(context!!, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent!!.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}

