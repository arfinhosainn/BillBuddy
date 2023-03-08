package com.example.billbuddy.services

import com.example.billbuddy.data.local.model.Payment

interface AlarmScheduler {
    fun schedule(item: Payment)
    fun cancel(item: Payment)
}