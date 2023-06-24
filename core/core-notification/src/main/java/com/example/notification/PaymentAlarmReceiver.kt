package com.example.notification

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.core_database.NotificationDao
import com.example.core_domain.repository.PaymentRepository
import com.example.util.model.Notification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class PaymentAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var paymentRepository: PaymentRepository

    @Inject
    lateinit var notificationDao: NotificationDao

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val paymentId = intent?.getStringExtra("PAYMENT_EXTRA") ?: return
        val notificationTitle = intent.getStringExtra("NOTIFICATION_TITLE")
        val notificationMessage = intent.getStringExtra("NOTIFICATION_MESSAGE")
        val notificationDate = intent.getStringExtra("NOTIFICATION_DATE")

        val insertNotifications = Notification(
            notificationTitle = notificationTitle!!,
            notificationDesc = notificationMessage!!,
            notificationDate = LocalDate.parse(notificationDate)
        )

        CoroutineScope(Dispatchers.IO).launch {
            notificationDao.insertNotifications(notification = insertNotifications)
        }

        notificationManager = NotificationManagerCompat.from(context!!)
        val notification = NotificationCompat.Builder(context, "bill_due")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission(context as Activity)
        } else {
            notificationManager.notify(0, notification)
        }
    }

    private fun requestNotificationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            PERMISSION_REQUEST_CODE
        )
    }
}
