package com.example.billbuddy.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

interface DateTimeProvider {
    fun now(): LocalDateTime
}

class DateTimeProviderImpl : DateTimeProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}