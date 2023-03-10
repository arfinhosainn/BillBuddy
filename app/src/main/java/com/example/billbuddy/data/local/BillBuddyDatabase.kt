package com.example.billbuddy.data.local

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.billbuddy.data.local.converter.DateConverter
import com.example.billbuddy.data.local.model.Expense
import com.example.billbuddy.data.local.model.Notification
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.data.local.model.TransactionDto


@Database(
    entities = [TransactionDto::class, Payment::class, Notification::class, Expense::class],
    version = 3,
    autoMigrations = [AutoMigration(
        from = 2,
        to = 3,
        spec = BillBuddyDatabase.Migration2to3::class
    )]
)
@TypeConverters(value = [DateConverter::class])
abstract class BillBuddyDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val paymentDao: PaymentDao
    abstract val notificationDao: NotificationDao
    abstract val expenseDao: ExpenseDao

    @DeleteColumn.Entries(
        DeleteColumn(
            tableName = "notification_table",
            columnName = "notificationDate"
        )
    )
    class Migration2to3 : AutoMigrationSpec

}