package com.example.billbuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey
    val notificationTitle: String,
    val notificationDesc: String
)
