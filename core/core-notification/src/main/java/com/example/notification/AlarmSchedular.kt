package com.example.notification

import com.example.util.model.Payment

interface AlarmScheduler {
    fun schedule(item: Payment)
    fun cancel(item: Payment)
}