package com.example.billbuddy.notification_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.NotificationsRepository
import com.example.billbuddy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {

    private val _notificationsState = MutableStateFlow(NotificationListState())
    val notificationsState = _notificationsState.asStateFlow()


    init {
        getAllNotifications()
    }

    private fun getAllNotifications() {
        notificationsRepository.getAllNotifications().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _notificationsState.value = NotificationListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _notificationsState.value = NotificationListState(isLoading = true)
                }
                is Resource.Success -> {
                    delay(5000L)
                    _notificationsState.value =
                        NotificationListState(payments = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}