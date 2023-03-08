package com.example.billbuddy.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.billbuddy.data.local.model.Payment
import java.time.ZoneOffset

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager: AlarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: Payment) {

        val intent = Intent(context, PaymentAlarmReceiver::class.java).apply {
            putExtra("PAYMENT_EXTRA", item.paymentTitle)
            putExtra("NOTIFICATION_TITLE", item.paymentTitle)
            putExtra("NOTIFICATION_MESSAGE", "Your payment is due today")
            Log.d("AndroidAlarmScheduler", "item id: ${item.id}")

        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.paymentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )
        )
    }

    override fun cancel(item: Payment) {
        val intent = Intent(context, PaymentAlarmReceiver::class.java).apply {
            putExtra("PAYMENT_EXTRA", item.id)
        }
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}