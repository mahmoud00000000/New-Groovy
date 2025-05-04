package com.example.groovyshopping.user.FCM

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.groovyshopping.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

//class MyFirebaseService : FirebaseMessagingService() {
//    override fun onNewToken(p0: String) {
//        print("token:$p0")
//        super.onNewToken(p0)
//    }

//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//        Log.v("onMessageReceived", remoteMessage.data.toString())
//        try {
//            val notifyIntent: Intent
//            if (remoteMessage.data != null) {
//                Log.v("WTF FCM data", "" + remoteMessage.data.toString())
//                when (remoteMessage.data["action_type"]) {
//                    "general" -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    }
//                    "store" -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.putExtra("type", "store")
//                        notifyIntent.putExtra("id", remoteMessage.data["action_id"]?.toIntOrNull())
//                        notifyIntent.putExtra("close", true)
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    }
//                    "category" -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.putExtra("type", "product")
//                        notifyIntent.putExtra("id", remoteMessage.data["action_id"]?.toIntOrNull())
//                        notifyIntent.putExtra("close", true)
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    }
//                    "order" -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.putExtra("type", "order")
//                        notifyIntent.putExtra("id", remoteMessage.data["action_id"]?.toIntOrNull())
//                        notifyIntent.putExtra("close", true)
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//
//                        val broadcast = Intent()
//                        broadcast.action = "REFRESH_ACTION"
//                        sendBroadcast(broadcast)
//                    }
//                    "external_link" -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.putExtra("type", "external_link")
//                        notifyIntent.putExtra("url", remoteMessage.data["action_id"]?.toString())
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    }
//                    else -> {
//                        notifyIntent = Intent(this, SplashActivity::class.java)
//                        notifyIntent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    }
//                }
//            } else {
//                notifyIntent = Intent(this, SplashActivity::class.java)
//                notifyIntent.flags =
//                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            }

//            if (remoteMessage.data.containsKey("image") && remoteMessage.data["image"] != null) {
//                initImageNotification(remoteMessage, notifyIntent,remoteMessage.data)
//            } else {
//                initDefaultNotification(remoteMessage, notifyIntent)
//            }
//        } catch (e: Exception) {
//            Log.v("WTF FCM Exception", e.message + ">>>")
//            e.printStackTrace()
//        }
//    }


//    private fun initImageNotification(
//        remoteMessage: RemoteMessage,
//        notifyIntent: Intent,
//        data: MutableMap<String, String>
//    ) {
//        try {
//
//            var bitmap: Bitmap? = getBitmapFromURL(data["image"])


//            val notifyPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
//                PendingIntent.getActivity(
//                    this,
//                    0,
//                    notifyIntent,
//                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                )
//            else
//                PendingIntent.getActivity(
//                    this,
//                    0,
//                    notifyIntent,
//                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
//                )

//            val mBuilder =
//                NotificationCompat.Builder(this)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(remoteMessage.data["title"])
////                .setContentText(remoteMessage.data["body"])
//                    .setStyle(
//                        NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap)
//                            .setSummaryText(remoteMessage.data["body"])
//                    )
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setAutoCancel(true)
//                    .setContentIntent(notifyPendingIntent)
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val name = getString(R.string.app_name)
//                val importance = NotificationManager.IMPORTANCE_DEFAULT
//                val channel = NotificationChannel(name, name, importance)
//                val notificationManager =
//                    getSystemService(
//                        NotificationManager::class.java
//                    )
//                notificationManager.createNotificationChannel(channel)
//                mBuilder.setChannelId(name)
//                notificationManager.notify(
//                    SystemClock.uptimeMillis().toInt(),
//                    mBuilder.build()
//                )
//            } else {
//                val notificationManager =
//                    NotificationManagerCompat.from(this)
//                notificationManager.notify(
//                    SystemClock.uptimeMillis().toInt(),
//                    mBuilder.build()
//                )
//            }
//        } catch (e: Exception) {
//            Log.v("WTF FCM Exception", e.message + ">>>")
//            e.printStackTrace()
//        }
//    }


//    private fun initDefaultNotification(
//        remoteMessage: RemoteMessage,
//        notifyIntent: Intent
//    ) {
//
//        try {
//
//            val notifyPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
//                PendingIntent.getActivity(
//                    this,
//                    0,
//                    notifyIntent,
//                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                )
//            else
//                PendingIntent.getActivity(
//                    this,
//                    0,
//                    notifyIntent,
//                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
//                )
//
//            val mBuilder =
//                NotificationCompat.Builder(this)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(remoteMessage.data["title"])
//                    .setStyle(
//                        NotificationCompat.BigTextStyle()
//                            .bigText(remoteMessage.data["body"])
//                    )
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setAutoCancel(true)
//                    .setContentIntent(notifyPendingIntent)
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val name = getString(R.string.app_name)
//                val importance = NotificationManager.IMPORTANCE_DEFAULT
//                val channel = NotificationChannel(name, name, importance)
//                val notificationManager =
//                    getSystemService(
//                        NotificationManager::class.java
//                    )
//                notificationManager.createNotificationChannel(channel)
//                mBuilder.setChannelId(name)
//                notificationManager.notify(
//                    SystemClock.uptimeMillis().toInt(),
//                    mBuilder.build()
//                )
//            } else {
//                val notificationManager =
//                    NotificationManagerCompat.from(this)
//                notificationManager.notify(
//                    SystemClock.uptimeMillis().toInt(),
//                    mBuilder.build()
//                )
//            }
//        } catch (e: Exception) {
//            Log.v("WTF FCM Exception", e.message + ">>>")
//            e.printStackTrace()
//        }
//    }


//    private fun getBitmapFromURL(src: String?): Bitmap? {
//        return try {
//            val url = URL(src)
//            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val input: InputStream = connection.inputStream
//            BitmapFactory.decodeStream(input)
//        } catch (e: IOException) {
//            // Log exception
//            Log.v("IOException", e.toString())
//            null
//        }
//    }
//}