package com.example.tp_unsplash.models

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

object DateTimeConverter {
    var df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    @TypeConverter
    fun fromTimestamp(value: String?): java.util.Date? {
        return if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }
}