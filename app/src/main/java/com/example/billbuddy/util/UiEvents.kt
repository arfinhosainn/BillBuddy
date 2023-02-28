package com.example.billbuddy.util

sealed class UIEvent {
    data class Alert(val info: String) : UIEvent()
    data class NoAlert(val info: String = String()) : UIEvent()
}
