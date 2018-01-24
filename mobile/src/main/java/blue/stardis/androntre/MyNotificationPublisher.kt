package blue.stardis.androntre

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Parcelable
import android.util.Log


class MyNotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = intent.getParcelableExtra<Parcelable>(NOTIFICATION) as Notification
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        Log.d("TEST", notificationId.toString())
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        var NOTIFICATION_ID = "notification_id"
        var NOTIFICATION = "notification"
    }
}