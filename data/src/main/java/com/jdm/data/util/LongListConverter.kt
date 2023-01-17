package com.jdm.trustme.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LongListConverter {
    @TypeConverter
    fun fromString(value: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson<List<Long>>(value, listType)
    }
    @TypeConverter
    fun fromList(list: List<Long>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}