package com.example.billbuddy.data.local.repository

import com.example.billbuddy.data.local.NotificationDao
import com.example.billbuddy.data.local.model.Notification
import com.example.billbuddy.domain.repository.NotificationsRepository
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationsRepository {
    override fun getAllNotifications(): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading())
            notificationDao.getAllNotification().collect { allNotifications ->
                emit(Resource.Success(allNotifications))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        }
    }
}