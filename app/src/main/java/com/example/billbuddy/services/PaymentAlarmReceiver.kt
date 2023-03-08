package com.example.billbuddy.services

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.billbuddy.R
import com.example.billbuddy.data.local.NotificationDao
import com.example.billbuddy.data.local.model.Notification
import com.example.billbuddy.domain.repository.PaymentRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PaymentAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var paymentRepository: PaymentRepository

    @Inject
    lateinit var notificationDao: NotificationDao

    private var notificationManager: NotificationManagerCompat? = null


    override fun onReceive(context: Context?, intent: Intent?) {
        val paymentId = intent?.getStringExtra("PAYMENT_EXTRA") ?: return
        val notificationTitle = intent.getStringExtra("NOTIFICATION_TITLE")
        val notificationMessage = intent.getStringExtra("NOTIFICATION_MESSAGE")


        val insertNotifications =
            Notification(
                notificationTitle = notificationTitle!!,
                notificationDesc = notificationMessage!!
            )

        Log.d("insertNotfication", "onReceive: $insertNotifications")

        CoroutineScope(Dispatchers.IO).launch {

            notificationDao.insertNotifications(notification = insertNotifications)
        }




        notificationManager = NotificationManagerCompat.from(context!!)
        val notification = NotificationCompat.Builder(context, "alarm_id")
            .setSmallIcon(R.drawable.mobile)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager?.notify(0, notification)
    }
}