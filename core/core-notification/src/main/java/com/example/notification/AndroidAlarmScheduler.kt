package com.example.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notification.PaymentAlarmReceiver
import com.example.util.model.Payment
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager: AlarmManager = context.getSystemService(AlarmManager::class.java)


    override fun schedule(item: Payment) {
        val intent = Intent(context, PaymentAlarmReceiver::class.java).apply {
            putExtra("PAYMENT_EXTRA", item.paymentTitle)
            putExtra("NOTIFICATION_TITLE", item.paymentTitle)
            putExtra("NOTIFICATION_MESSAGE", "Your payment is due today")
            putExtra("NOTIFICATION_DATE", item.paymentDate.toString())
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