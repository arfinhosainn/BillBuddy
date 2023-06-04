package com.example.core_domain.repository

import com.example.util.model.Notification
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {

    fun getAllNotifications(): Flow<Resource<List<Notification>>>
}