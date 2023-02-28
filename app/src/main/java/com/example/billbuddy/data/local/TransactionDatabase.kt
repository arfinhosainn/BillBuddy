package com.example.billbuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.billbuddy.data.local.converter.DateConverter
import com.example.billbuddy.data.local.model.TransactionDto


@Database(entities = [TransactionDto::class], exportSchema = true, version = 1)
@TypeConverters(value = [DateConverter::class])
abstract class TransactionDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
}