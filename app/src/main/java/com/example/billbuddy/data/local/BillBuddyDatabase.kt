package com.example.billbuddy.data.local

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.billbuddy.data.local.converter.DateConverter
import com.example.billbuddy.data.local.model.*

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
    ]
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