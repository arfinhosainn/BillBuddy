package com.example.billbuddy.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun formDate(date: Date?): Long? {
        return date?.time
    }

    private val formatter = DateTimeFormatter.ofPattern("MM dd yyyy")

    @TypeConverter
    fun fromLocalDateTime(value: LocalDate?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }


    private val localDateTime = DateTimeFormatter.ofPattern("MM dd yyyy hh:mm:ss a")


    @TypeConverter
    fun fromLocalDate(date: LocalDateTime?): String? {
        return date?.format(localDateTime)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDateTime? {
        return dateString?.let {
            LocalDateTime.parse(it, localDateTime)
        }
    }


}