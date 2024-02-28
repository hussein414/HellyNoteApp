package com.example.hellynoteapp.data.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DateTimeTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(value)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
    @TypeConverter
    fun fromTime(time: LocalTime?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun toTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it) }
    }
}

