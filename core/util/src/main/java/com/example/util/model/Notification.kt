package com.example.util.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey
    val notificationTitle: String,
    val notificationDesc: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val notificationDate: LocalDate = LocalDate.now()
)
