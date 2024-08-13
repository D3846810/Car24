package uk.ac.tees.mad.d3846810.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.activities.SplashActivity

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("TAG_FCM_MESSAGING", "From: $remoteMessage")
//
//        // Check if message contains a data payload.
//        remoteMessage.data.isNotEmpty().let {
//            // Compose and show notification
//            if (remoteMessage.data.isNotEmpty()) {
//                val title: String = remoteMessage.data["title"].toString()
//                val msg: String = remoteMessage.data["message"].toString()
//                sendNotification(title, msg)
//            }
//
//        }
////
////        // Check if message contains a notification payload.
//        remoteMessage.notification?.let {
//            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
//        }

    }

    val TAG = String::class.java.simpleName

    private fun sendNotification(title: String?, messageBody: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = "channel_1"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.icon)
                .setContentTitle(title).setContentText(messageBody).setAutoCancel(true)
                .setSound(defaultSoundUri).setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        val channel = NotificationChannel(
            channelId, "Car24Admin", NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, notificationBuilder.build())
    }


}