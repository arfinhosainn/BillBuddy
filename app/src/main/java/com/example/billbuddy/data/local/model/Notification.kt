package com.example.billbuddy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey
    val notificationTitle: String,
    val notificationDesc: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val notificationDate: LocalDate = LocalDate.now()
)
