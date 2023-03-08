package com.example.billbuddy.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.billbuddy.data.local.converter.DateConverter
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.data.local.model.TransactionDto


@Database(
    entities = [TransactionDto::class, Payment::class], version = 1
)
@TypeConverters(value = [DateConverter::class])
abstract class BillBuddyDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val paymentDao: PaymentDao
}