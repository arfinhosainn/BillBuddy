package com.example.billbuddy.notification_screen

import com.example.util.model.Notification

data class NotificationListState(
    val isLoading: Boolean = false,
    val payments: List<Notification> = emptyList(),
    val error: String = ""
)