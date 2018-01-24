package blue.stardis.androntre

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.NotificationCompat
import android.util.Log


class MyNotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TEST3", intent.toString())

        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        val notificationTitre = intent.getStringExtra(NOTIFICATION_TITLE)
        val notificationDesc = intent.getStringExtra(NOTIFICATION_CONTENT)


        val notification = NotificationCompat.Builder(context)
                .setContentTitle(notificationTitre)
                .setContentText(notificationDesc)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build()

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(id, notification)
    }

    companion object {
        var NOTIFICATION_ID = "id"
        var NOTIFICATION_TITLE = "titre"
        var NOTIFICATION_CONTENT = "description"
    }
}