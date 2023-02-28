package com.example.billbuddy.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun formDate(date: Date?): Long? {
        return date?.time
    }

}