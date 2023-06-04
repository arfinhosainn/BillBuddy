package com.example.core_database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.core_database.converter.DateConverter
import com.example.billbuddy.data.local.model.*
import com.example.util.model.Expense
import com.example.util.model.Notification
import com.example.util.model.Payment
import com.example.util.model.PaymentHistory
import com.example.util.model.TransactionDto

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities =
    [
        TransactionDto::class,
        Payment::class,
        Notification::class,
        Expense::class,
        PaymentHistory::class
    ],
    version = 4,
    autoMigrations = [
        AutoMigration(
            from = 3,
            to = 4,
            spec = BillBuddyDatabase.Migration2to3::class
        )
    ],
    exportSchema = true
)
@TypeConverters(value = [DateConverter::class])
abstract class BillBuddyDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val paymentDao: PaymentDao
    abstract val notificationDao: NotificationDao
    abstract val expenseDao: ExpenseDao
    abstract val paymentHistoryDao: PaymentHistoryDao

    @DeleteColumn.Entries(
        DeleteColumn(
            tableName = "notification_table",
            columnName = "notificationDate"
        )
    )
    class Migration2to3 : AutoMigrationSpec
}