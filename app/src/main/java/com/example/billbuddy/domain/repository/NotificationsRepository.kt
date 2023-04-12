package com.example.billbuddy.domain.repository

import com.example.billbuddy.data.local.model.Notification
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {

    fun getAllNotifications(): Flow<Resource<List<Notification>>>
}