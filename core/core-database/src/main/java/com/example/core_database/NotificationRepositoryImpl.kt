package com.example.core_database

import com.example.util.model.Notification
import com.example.billbuddy.util.Resource
import com.example.core_domain.repository.NotificationsRepository
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