package com.example.billbuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.billbuddy.data.local.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notification: Notification)

    @Query("SELECT * FROM notification_table")
    fun getAllNotification(): Flow<List<Notification>>
}